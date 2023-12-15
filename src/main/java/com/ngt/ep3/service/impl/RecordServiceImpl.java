package com.ngt.ep3.service.impl;

import com.ngt.ep3.model.Record;
import com.ngt.ep3.repository.RecordRepository;
import com.ngt.ep3.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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
        return repository.findById(id).orElse(null);
    }

    @Override
    public Record addRecord(Record record) {
        return repository.save(record);
    }

    @Override
    public Record updateRecord(Record record) {
        Record existingRecord = repository
                .findById(record.getId())
                .orElseThrow(NoSuchElementException::new);
        existingRecord.setFields(record.getFields());
        existingRecord.setValues(record.getValues());
        existingRecord.setRecordName(record.getRecordName());
        return repository.save(existingRecord);
    }

    @Override
    public void deleteRecord(int id) {
        repository.deleteById(id);
    }
}
