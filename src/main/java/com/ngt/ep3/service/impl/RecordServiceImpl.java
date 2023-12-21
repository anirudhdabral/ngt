package com.ngt.ep3.service.impl;

import com.ngt.ep3.model.Record;
import com.ngt.ep3.model.response_DTO.BackendResponse;
import com.ngt.ep3.repository.RecordRepository;
import com.ngt.ep3.service.RecordService;
import com.ngt.ep3.utility.ForecasterUtility;
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
        return repository.findAll().stream().map(item -> item.getRecordName()).distinct().collect(Collectors.toList());
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
    public BackendResponse getForecastedResults() {
        List<Record> records = repository.findAll();

        Map<String, Map<String, Map<String, Double>>> sumTotalTimeframeAndFieldElement = ForecasterUtility.getGroupedTotalByTimeframe(records);
        Map<String, Map<String, Double>> sumTotalRecordName = ForecasterUtility.getRecordNameTotalByTimeframe(records);
        Map<String, Double> sumTotalTimeframe = ForecasterUtility.getGrandTotalByTimeframe(records);

        return BackendResponse.builder()
                .recordList(records)
                .groupedTotalByTimeframe(sumTotalTimeframeAndFieldElement)
                .recordNameTotalByTimeframe(sumTotalRecordName)
                .grandTotalByTimeframe(sumTotalTimeframe)
                .build();
    }

    @Override
    public String nextYearForcastedResult() {
        List<Record> all = repository.findAll();
        ForecasterUtility.distributeValues(all, "country1", "male", 2024, 24 );

        return "nextYearForcastedResult API is under construction: ";
    }
}
