package com.kras.exploremicroservice;

import com.kras.exploremicroservice.service.TourPackageService;
import com.kras.exploremicroservice.service.TourService;
import java.io.IOException;
import java.util.List;

import com.kras.exploremicroservice.util.TourFromFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ExploremicroserviceApplication implements CommandLineRunner {

    @Value("${exploremicroservice.importfile}")
    private String importFile;

    @Autowired
    private TourPackageService tourPackageService;
    @Autowired
    private TourService tourService;

    public static void main(String[] args) {
        SpringApplication.run(ExploremicroserviceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
       // createTourPackages();
      //  createTours(importFile);
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
        read.forEach(importedTour -> tourService.createTour(
                importedTour.getTitle(),
                importedTour.getPackageName(),
                importedTour.getDetails()
                )
        );
    }
}
