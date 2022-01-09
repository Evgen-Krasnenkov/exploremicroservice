package com.kras.exploremicroservice.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

    @Override
    public String toString() {
        return "TourFromFile{" +
                "title='" + title + '\'' +
                ", packageName='" + packageName + '\'' +
                ", details=" + details +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TourFromFile that = (TourFromFile) o;
        return Objects.equals(title, that.title) && Objects.equals(packageName, that.packageName) && Objects.equals(details, that.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, packageName, details);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }
}