package com.kras.exploremicroservice.repo;

import com.kras.exploremicroservice.domain.Tour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface TourRepository extends CrudRepository<Tour, Integer> {
}
