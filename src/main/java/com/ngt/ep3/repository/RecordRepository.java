package com.ngt.ep3.repository;

import com.ngt.ep3.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {
    List<Record> findAllByRecordName(String recordName);
}
