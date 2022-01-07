package com.kras.exploremicroservice.repo;

import com.kras.exploremicroservice.model.TourRating;
import com.kras.exploremicroservice.model.TourRatingPk;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface TourRatingRepository extends CrudRepository<TourRating, TourRatingPk> {
    List<TourRating> findByPkTourId(Integer tourId);

    Optional<TourRating> findByTourIdAndPkCustomerId(Integer tourId, Integer customerId);
}
