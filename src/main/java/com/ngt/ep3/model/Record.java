package com.ngt.ep3.model;

import com.ngt.ep3.model.embeddable.RecordFields;
import com.ngt.ep3.model.embeddable.RecordValues;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ngt_record")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String recordName;

    @ElementCollection
    @CollectionTable(name = "record_fields", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "record_fields")
    private List<RecordFields> fields;

    @ElementCollection
    @CollectionTable(name = "record_values", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "record_values")
    private List<RecordValues> values;
}
