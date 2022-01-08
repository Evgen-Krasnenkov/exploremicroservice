package com.kras.exploremicroservice.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document
@Getter
@EqualsAndHashCode
@ToString
public class Tour {
    @Id
    private String id;
    @Indexed
    private String title;
    private String tourPackageCode;
    private String tourPackageName;
    private Map<String, String> details;

    public Tour(String title, TourPackage tourPackage, Map<String, String> details) {
        this.title = title;
        this.details = details;
        this.tourPackageCode = tourPackage.getCode();
        this.tourPackageName = tourPackage.getName();
    }

    public Tour() {
    }

}
