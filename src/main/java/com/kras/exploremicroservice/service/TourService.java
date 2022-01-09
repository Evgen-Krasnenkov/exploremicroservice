package com.kras.exploremicroservice.service;

import com.kras.exploremicroservice.model.Tour;
import com.kras.exploremicroservice.model.TourPackage;
import com.kras.exploremicroservice.repo.TourPackageRepository;
import com.kras.exploremicroservice.repo.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TourService {
    private TourRepository tourRepository;
    private TourPackageRepository tourPackageRepository;

    @Autowired
    public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
        this.tourRepository = tourRepository;
        this.tourPackageRepository = tourPackageRepository;
    }

    public Tour createTour(String title, String tourPackageName, Map<String, String>details) {
        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName)
                .orElseThrow(() -> new RuntimeException("This package does not exist: " + tourPackageName));
        return tourRepository.save(new Tour(title, tourPackage, details));
    }
    public long total() {
        return tourRepository.count();
    }
}
