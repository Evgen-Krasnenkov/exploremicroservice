package com.kras.exploremicroservice.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kras.exploremicroservice.model.Difficulty;
import com.kras.exploremicroservice.model.Region;
import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

@Getter
public class TourFromFile {
    String title;
    String packageName;
    Map<String, String> details;

    TourFromFile(Map<String, String> record) {
        this.title = record.get("title");
        this.packageName = record.get("packageType");
        this.details = record;
        this.details.remove("PackageType");
        this.details.remove("title");
    }

    public static List<TourFromFile> read(String fileToImport) throws IOException {
        List<Map<String, String>> maps = new ObjectMapper().setVisibility(FIELD, ANY).
                readValue(new FileInputStream(fileToImport),
                        new TypeReference<List<Map<String, String>>>() {
                        });
        return maps.stream()
                .map(TourFromFile::new)
                .collect(Collectors.toList());
    }
}