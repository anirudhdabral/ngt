package com.ngt.ep3.controller;

import com.ngt.ep3.model.Record;
import com.ngt.ep3.model.TimeframeTotal;
import com.ngt.ep3.model.response_DTO.BackendResponse;
import com.ngt.ep3.service.impl.RecordServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ngt")
@CrossOrigin
public class RecordController {
    private final RecordServiceImpl service;

    public RecordController(RecordServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/recordNames")
    public ResponseEntity<List<String>> getAllRecordNames() {
        return new ResponseEntity<>(service.getAllRecordNames(), HttpStatus.OK);
    }

    @GetMapping("/records")
    public ResponseEntity<List<Record>> getAllRecords() {
        List<Record> allRecords = service.getAllRecords();
        return new ResponseEntity<>(allRecords, HttpStatus.OK);
    }

    @GetMapping("/recordName/{recordName}")
    public ResponseEntity<List<Record>> getAllByRecordName(@PathVariable String recordName) {
        return new ResponseEntity<>(service.findAllByRecordName(recordName), HttpStatus.OK);
    }


    @GetMapping("/records/{id}")
    public ResponseEntity<Record> getRecordById(@PathVariable int id) {
        Record record = service.getRecord(id);
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

    @PostMapping("/addRecord")
    public ResponseEntity<Record> addRecord(@RequestBody Record record) {
        Record record1 = service.addRecord(record);
        return new ResponseEntity<>(record1, HttpStatus.OK);
    }

    @PutMapping("/updateRecord")
    public ResponseEntity<Record> updateRecord(@RequestBody Record record) {
        Record record1 = service.updateRecord(record);
        return new ResponseEntity<>(record1, HttpStatus.OK);
    }

    @DeleteMapping("/records/{id}")
    public ResponseEntity<Boolean> deleteRecord(@PathVariable int id) {
        boolean deleted = service.deleteRecord(id);
        return new ResponseEntity<>(deleted, HttpStatus.OK);
    }

    @GetMapping("/getForcastedResults")
    public ResponseEntity<BackendResponse> getForcastedResults() {
        BackendResponse forecastedResults = service.getForecastedResults();
        return new ResponseEntity<>(forecastedResults, HttpStatus.OK);
    }

    @PostMapping("/nextYearForcastedResult")
    public ResponseEntity<String> nextYearForcastedResult() {

        String forecastedResults = service.nextYearForcastedResult();
//        System.out.println("nextYearForcastedResult API is under construction.");
        return new ResponseEntity<>(forecastedResults, HttpStatus.OK);
    }

    @PutMapping("/addTimeframeTotal/{recordName}")
    public ResponseEntity<String> addTimeframeTotal(@RequestBody TimeframeTotal timeframeTotal, @PathVariable String recordName) {
        return new ResponseEntity<>(service.addTimeframeTotal(timeframeTotal, recordName), HttpStatus.OK);
    }
}
