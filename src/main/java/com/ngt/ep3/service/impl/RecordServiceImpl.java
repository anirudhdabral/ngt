package com.ngt.ep3.service.impl;

import com.ngt.ep3.model.Record;
import com.ngt.ep3.model.response_DTO.BackendResponse;
import com.ngt.ep3.repository.RecordRepository;
import com.ngt.ep3.service.RecordService;
import com.ngt.ep3.utility.CountryTotal;
import com.ngt.ep3.utility.GenderTotal;
import com.ngt.ep3.utility.GrandTotal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordRepository repository;


    @Override
    public List<Record> getAllRecords() {
        return repository.findAll();
    }

    @Override
    public Record getRecord(int id) {
        Optional<Record> recordById = repository.findById(id);
        if (recordById.isEmpty()) {
            throw new RuntimeException("No records found with id: " + id);
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
            throw new RuntimeException("Update failed, No such record found with id: " + record.getId());
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
        List<Record> all = repository.findAll();

        Map<String, Map<String, Map<String, Double>>> sumByYearAndGender = GenderTotal.getAllByGender(all);
        Map<String, Map<String, Double>> countryTotal = CountryTotal.getCountryTotal(all);
        Map<String, Double> grandTotalByYear = GrandTotal.grandTotalByYear(all);

        return BackendResponse.builder()
                .recordList(all)
                .genderTotalByYear(sumByYearAndGender)
                .countryTotalByYear(countryTotal)
                .grandTotalByYear(grandTotalByYear)
                .build();
    }
}
