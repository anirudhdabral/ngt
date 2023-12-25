package com.ngt.ep3.service.impl;

import com.ngt.ep3.model.Record;
import com.ngt.ep3.model.TimeframeTotal;
import com.ngt.ep3.model.embeddable.RecordFields;
import com.ngt.ep3.model.embeddable.RecordValues;
import com.ngt.ep3.repository.RecordRepository;
import com.ngt.ep3.service.RecordService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ngt.ep3.constants.NgtEp3Constants.NOT_FOUND_RECORD;
import static com.ngt.ep3.constants.NgtEp3Constants.UPDATE_FAILED;

@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository repository;

    public RecordServiceImpl(RecordRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<String> getAllRecordNames() {
        return repository.findAll().stream().map(Record::getRecordName).distinct().collect(Collectors.toList());
    }

    @Override
    public List<Record> findAllByRecordName(String recordName) {
        return repository.findAllByRecordName(recordName);
    }

    @Override
    public List<Record> getAllRecords() {
        return repository.findAll();
    }

    @Override
    public Record getRecord(int id) {
        Optional<Record> recordById = repository.findById(id);
        if (recordById.isEmpty()) {
            throw new RuntimeException(NOT_FOUND_RECORD + id);
        }
        return recordById.get();
    }

    @Override
    public Record addRecord(Record record) {
        return repository.save(record);
    }

    @Override
    public Record updateRecord(Record record) {
        Optional<Record> existingRecord = repository.findById(record.getId());
        if (existingRecord.isEmpty()) {
            throw new RuntimeException(UPDATE_FAILED + record.getId());
        }
        existingRecord.get().setFields(record.getFields());
        existingRecord.get().setValues(record.getValues());
        existingRecord.get().setRecordName(record.getRecordName());
        return repository.save(existingRecord.get());
    }

    @Override
    public boolean deleteRecord(int id) {
        try {
            repository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public String addTimeframeTotal(TimeframeTotal timeframeTotal, String recordName) {
        //noinspection ResultOfMethodCallIgnored
        repository.findAllByRecordName(recordName)
                .stream()
                .flatMap(record ->
                        record.getFields()
                                .stream()
                                .map(RecordFields::getElement)
                )
                .distinct()
                .peek(timeframeName -> {
                    if (timeframeName.equals(timeframeTotal.getTimeframeName())) {
                        throw new RuntimeException("Timeframe already exists: " + timeframeTotal.getTimeframeName());
                    }
                });

        Map<Integer, Double> idToElementValueMap = repository.findAllByRecordName(recordName).stream()
                .collect(Collectors.toMap(Record::getId, record ->
                        record.getValues()
                                .get(record.getValues().size() - 1)
                                .getValue()
                ));

        double sumOriginalValues = idToElementValueMap.values()
                .stream()
                .mapToDouble(Double::doubleValue)
                .sum();

        for (Map.Entry<Integer, Double> entry : idToElementValueMap.entrySet()) {
            int id = entry.getKey();
            double originalValue = entry.getValue();
            double ratio = originalValue / sumOriginalValues;
            double distributedValue = ratio * timeframeTotal.getTimeframeTotal();
            Record tempRecord = repository.findById(id).orElse(null);
            if (tempRecord != null) {
                List<RecordValues> updatedRecordValuesList = tempRecord.getValues();
                updatedRecordValuesList.add(new RecordValues(timeframeTotal.getTimeframeName(), distributedValue));
                tempRecord.setValues(updatedRecordValuesList);
                repository.save(tempRecord);
            }
        }

        return "SUCCESS";
    }
}
