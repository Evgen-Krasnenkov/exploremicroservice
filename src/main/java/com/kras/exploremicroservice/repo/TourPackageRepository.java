package com.kras.exploremicroservice.repo;

import com.kras.exploremicroservice.model.TourPackage;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "packages", path = "packages")
public interface TourPackageRepository extends CrudRepository<TourPackage, String> {
    Optional<TourPackage> findByName(@Param("name") String name);

    @Override
    @RestResource(exported = false)
    <S extends TourPackage> S save(S entity);

    @Override
    @RestResource(exported = false)
    <S extends TourPackage> Iterable<S> saveAll(Iterable<S> entities);

    @Override
    @RestResource(exported = false)
    void deleteById(String s);

    @Override
    @RestResource(exported = false)
    void delete(TourPackage entity);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends TourPackage> entities);

    @Override
    @RestResource(exported = false)
    void deleteAll();

/*
    @Query("Select t from Tour t where t.tourPackage.code = ?1 and t.difficulty = ?2 and " +
            " t.region = ?3 and t.price <= ?4")
    List<Tour> lookupTour(String code, Difficulty difficulty, Region region, Integer maxPrice);

    List<Tour> findByTourPackageCodeAndDifficultyAndRegionAndPriceMoreThan
            (String code, Difficulty difficulty, Region region, Integer minPrice);
    */
}
