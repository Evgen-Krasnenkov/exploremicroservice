package com.kras.exploremicroservice.repo;

import com.kras.exploremicroservice.domain.Difficulty;
import com.kras.exploremicroservice.domain.Region;
import com.kras.exploremicroservice.domain.Tour;
import com.kras.exploremicroservice.domain.TourPackage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface TourPackageRepository extends CrudRepository<TourPackage, String> {
    Optional<TourPackage> findByName(String name);
/*
    @Query("Select t from Tour t where t.tourPackage.code = ?1 and t.difficulty = ?2 and " +
            " t.region = ?3 and t.price <= ?4")
    List<Tour> lookupTour(String code, Difficulty difficulty, Region region, Integer maxPrice);

    List<Tour> findByTourPackageCodeAndDifficultyAndRegionAndPriceMoreThan
            (String code, Difficulty difficulty, Region region, Integer minPrice);

 */
}
