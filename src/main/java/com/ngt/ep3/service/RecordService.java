package com.ngt.ep3.service;

import com.ngt.ep3.model.Record;

import java.util.List;

public interface RecordService {
    List<Record> getAllRecords();

    Record getRecord(int id);

    Record addRecord(Record record);

    Record updateRecord(Record record);

    boolean deleteRecord(int id);
}
