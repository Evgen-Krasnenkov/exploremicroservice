package com.kras.exploremicroservice.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class TourPackage {
    @Id
    private String code;
    @Indexed
    private String name;

    public TourPackage() {
    }
    public TourPackage(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
