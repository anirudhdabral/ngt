package com.ngt.ep3;

import com.ngt.ep3.model.Record;
import com.ngt.ep3.model.embeddable.RecordFields;
import com.ngt.ep3.model.embeddable.RecordValues;
import com.ngt.ep3.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@RestController
public class NgtEp3Application {
	@Autowired
	private RecordRepository repository;

	@GetMapping("/populate")
	public Record populate(){
		List<RecordFields> rfl = Arrays.asList(
				new RecordFields("Gender","Male"),
				new RecordFields("Age-Group","10-20")
		);
		List<RecordValues> rvl = Arrays.asList(
				new RecordValues("2019",1),
				new RecordValues("2020",4)
		);
		Record r = new Record(1,"India",rfl,rvl);
		return repository.save(r);
	}

	@GetMapping("/get")
	public List<Record> get(){
		return repository.findAll();
	}

	public static void main(String[] args) {
		SpringApplication.run(NgtEp3Application.class, args);


	}

}
