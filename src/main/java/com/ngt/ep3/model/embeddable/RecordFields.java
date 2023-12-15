package com.ngt.ep3.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RecordFields {
    @Column(name = "element_name")
    private String element;

    @Column(name = "element_value")
    private String value;
}
