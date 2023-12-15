package com.ngt.ep3.controller;

import com.ngt.ep3.model.Record;
import com.ngt.ep3.service.impl.RecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecordController {
    @Autowired
    private RecordServiceImpl service;

    @GetMapping("/records")
    public List<Record> getAllRecords() {
        return service.getAllRecords();
    }

    @GetMapping("/records/{id}")
    public Record getRecord(@PathVariable int id) {
        return service.getRecord(id);
    }

    @PostMapping("/addRecord")
    public Record addRecord(@RequestBody Record record) {
        return service.addRecord(record);
    }

    @PutMapping("/updateRecord")
    public Record updateRecord(@RequestBody Record record) {
        return service.updateRecord(record);
    }

    @DeleteMapping("/records/{id}")
    public void deleteRecord(@PathVariable int id) {
        service.deleteRecord(id);
    }
}
