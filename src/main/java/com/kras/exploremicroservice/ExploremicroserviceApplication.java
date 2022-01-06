package com.kras.exploremicroservice;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.TypeRef;
import com.kras.exploremicroservice.domain.Difficulty;
import com.kras.exploremicroservice.domain.Region;
import com.kras.exploremicroservice.service.TourPackageService;
import com.kras.exploremicroservice.service.TourService;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@AllArgsConstructor
@SpringBootApplication
public class ExploremicroserviceApplication implements CommandLineRunner {

   // @Value("${exploremicroservice.importfile}")
   // private String importFile;

    //@Autowired
    private TourPackageService tourPackageService;
  //  @Autowired
    private TourService tourService;

    public static void main(String[] args) {
        SpringApplication.run(ExploremicroserviceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        createTourPackages();
        long numOfPackages = tourPackageService.total();
        createTours("ExploreCalifornia.json");
        long numOfTours = tourService.total();
    }
    private void createTourPackages() {
        tourPackageService.createTourPackage("BC", "Backpacking Cal");
        tourPackageService.createTourPackage("CC", "California South");
        tourPackageService.createTourPackage("CH", "California Hot");
        tourPackageService.createTourPackage("CY", "Cycle California");
        tourPackageService.createTourPackage("DS", "From Destination");
        tourPackageService.createTourPackage("KC", "Kids California");
        tourPackageService.createTourPackage("NW", "Nature Walking");
        tourPackageService.createTourPackage("SC", "SnowBoarding");
        tourPackageService.createTourPackage("TC", "Taste Of California");
    }

    private void createTours(String fileToImport) throws IOException {
        TourFromFile.read(fileToImport).forEach(importedTour ->
                tourService.createTour(importedTour.getTitle(),
                        importedTour.getDescription(),
                        importedTour.getBlurb(),
                        importedTour.getPrice(),
                        importedTour.getLength(),
                        importedTour.getBullets(),
                        importedTour.getKeywords(),
                        importedTour.getPackageType(),
                        importedTour.getDifficulty(),
                        importedTour.getRegion()));

    }
    private class TourFromFile {
        private String packageType, title, description, blurb, price, length,
                 bullets, keywords, difficulty, region;

        static List<TourFromFile> read(String fileToImport) throws IOException {
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
}
