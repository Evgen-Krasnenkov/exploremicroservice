package com.kras.exploremicroservice;

import com.kras.exploremicroservice.model.TourRating;
import com.kras.exploremicroservice.service.TourPackageService;
import com.kras.exploremicroservice.service.TourService;
import java.io.IOException;
import java.util.List;

import com.kras.exploremicroservice.util.TourFromFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ExploremicroserviceApplication implements CommandLineRunner {

    @Value("${exploremicroservice.importfile}")
    private String importFile;

    private TourPackageService tourPackageService;
    private TourService tourService;

    public ExploremicroserviceApplication(TourPackageService tourPackageService, TourService tourService) {
        this.importFile = importFile;
        this.tourPackageService = tourPackageService;
        this.tourService = tourService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExploremicroserviceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        createTourPackages();
        long numOfPackages = tourPackageService.total();
        createTours(importFile);
        long numOfTours = tourService.total();
    }
    private void createTourPackages() {
        tourPackageService.createTourPackage("BC", "Backpack Cal");
        tourPackageService.createTourPackage("CC", "California Calm");
        tourPackageService.createTourPackage("CH", "California Hot springs");
        tourPackageService.createTourPackage("CY", "Cycle California");
        tourPackageService.createTourPackage("DS", "From Desert to Sea");
        tourPackageService.createTourPackage("KC", "Kids California");
        tourPackageService.createTourPackage("NW", "Nature Watch");
        tourPackageService.createTourPackage("SC", "Snowboard Cali");
        tourPackageService.createTourPackage("TC", "Taste of California");
    }
    private void createRating(){

    }

    private void createTours(String fileToImport) throws IOException {
        List<TourFromFile> read = TourFromFile.read(fileToImport);
        read.forEach(importedTour -> tourService.createTour(importedTour.getTitle(),
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
}
