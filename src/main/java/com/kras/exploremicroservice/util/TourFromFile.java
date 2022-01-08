package com.kras.exploremicroservice.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kras.exploremicroservice.model.Difficulty;
import com.kras.exploremicroservice.model.Region;
import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

public class TourFromFile {
        private String packageType, title, description, blurb, price, length,
                 bullets, keywords, difficulty, region;

        public static List<TourFromFile> read(String fileToImport) throws IOException {
            return new ObjectMapper().setVisibility(FIELD, ANY).
                    readValue(new FileInputStream(fileToImport), new TypeReference<List<TourFromFile>>() {});
        }

        public TourFromFile() {
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getPackageType() {
            return packageType;
        }

        public void setPackageType(String packageType) {
            this.packageType = packageType;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBlurb() {
            return blurb;
        }

        public void setBlurb(String blurb) {
            this.blurb = blurb;
        }

        public Integer getPrice() {
            return Integer.valueOf(price);
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBullets() {
            return bullets;
        }

        public void setBullets(String bullets) {
            this.bullets = bullets;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public Region getRegion() {
            return Region.findByLabel(region);
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public Difficulty getDifficulty() {

            return Difficulty.valueOf(difficulty);
        }

        public void setDifficulty(String difficulty) {
            this.difficulty = difficulty;
        }
    }