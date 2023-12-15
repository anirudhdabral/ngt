package com.ngt.ep3.repository;

import com.ngt.ep3.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Integer> {
}
