package com.ngt.ep3.service;

import com.ngt.ep3.model.Record;
import com.ngt.ep3.model.TimeframeTotal;
import com.ngt.ep3.model.response_DTO.BackendResponse;

import java.util.List;

public interface RecordService {

    List<String> getAllRecordNames();

    List<Record> findAllByRecordName(String recordName);

    List<Record> getAllRecords();

    Record getRecord(int id);

    Record addRecord(Record record);

    Record updateRecord(Record record);

    boolean deleteRecord(int id);

    String addTimeframeTotal(TimeframeTotal timeframeTotal, String recordName);
    String addGroupTimeframeTotal(TimeframeTotal timeframeTotal, String recordName,String groupName);

    BackendResponse getForecastedResults();

}
