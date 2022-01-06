package com.kras.exploremicroservice.service;

import com.kras.exploremicroservice.domain.Difficulty;
import com.kras.exploremicroservice.domain.Region;
import com.kras.exploremicroservice.domain.Tour;
import com.kras.exploremicroservice.domain.TourPackage;
import com.kras.exploremicroservice.repo.TourPackageRepository;
import com.kras.exploremicroservice.repo.TourRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TourService {
    private TourRepository tourRepository;
    private TourPackageRepository tourPackageRepository;

    public Tour createTour(String title, String description, String blurb, Integer price,
                           String duration, String bullets, String keywords,
                           String tourPackageName, Difficulty difficulty, Region region) {
        TourPackage tourPackage = tourPackageRepository.findByName(tourPackageName)
                .orElseThrow(() -> new RuntimeException("This package does not exist: " + tourPackageName));
        return tourRepository.save(
                new Tour(title, description, blurb, price, duration, bullets, keywords,
                        tourPackage, difficulty, region));
    }
    public long total() {
        return tourRepository.count();
    }
}
