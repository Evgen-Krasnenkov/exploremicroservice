package com.kras.exploremicroservice.controller;

import com.kras.exploremicroservice.model.Tour;
import com.kras.exploremicroservice.model.TourRating;
import com.kras.exploremicroservice.model.TourRatingPk;
import com.kras.exploremicroservice.model.dto.RatingDto;
import com.kras.exploremicroservice.repo.TourRatingRepository;
import com.kras.exploremicroservice.repo.TourRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/tours/{tourId}/ratings")
public class TourRatingController {
    private static final String AVERAGE = "average";
    TourRatingRepository tourRatingRepository;
    TourRepository tourRepository;

    public TourRatingController(TourRatingRepository tourRatingRepository, TourRepository tourRepository) {
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
   public void createTourRating(@PathVariable(value = "tourId") int tourId,
                                @RequestBody @Validated RatingDto ratingDto) {
        Tour tour = verifyTour(tourId);
        tourRatingRepository.save(new TourRating(new TourRatingPk(tour, ratingDto.getCustomerId()),
                ratingDto.getScore(), ratingDto.getComment()));
   }

   @GetMapping
   public Page<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId,
                                               Pageable pageable) {
        verifyTour(tourId);
        Page<TourRating> tourRatings = tourRatingRepository.findByPkTourId(tourId, pageable);
        return new PageImpl<>(
                tourRatings.get().map(RatingDto::new)
                        .collect(Collectors.toList()),
                pageable,
                tourRatings.getTotalElements()
        );
   }

   @GetMapping(path = "/average")
   public Map<String, Double> getAverage(@PathVariable(value = "tourId") int tourId) {
        verifyTour(tourId);
        return Map.of(AVERAGE, tourRatingRepository.findByPkTourId(tourId).stream()
                .mapToLong(TourRating::getScore)
                .average()
                .orElseThrow(() -> new NoSuchElementException("Tour has no Ratings")));
   }

   @PutMapping
   public RatingDto updateWithPut(@PathVariable(value = "tourId") int tourId,
                                  @RequestBody @Validated RatingDto ratingDto) {
        TourRating rating = verifyTourRating(tourId, ratingDto.getCustomerId());
        rating.setScore(ratingDto.getScore());
        rating.setComment(ratingDto.getComment());
        return new RatingDto(tourRatingRepository.save(rating));
   }

   @PatchMapping
   public  RatingDto updateWithPatch(@PathVariable(value = "tourId") int tourId,
                                     @RequestBody @Validated RatingDto ratingDto) {
       TourRating rating = verifyTourRating(tourId, ratingDto.getCustomerId());
       if (rating.getScore() != null) {
           rating.setScore(ratingDto.getScore());
       }
       if (rating.getComment() != null) {
           rating.setComment(ratingDto.getComment());
       }
       return new RatingDto(tourRatingRepository.save(rating));
   }

   @DeleteMapping(path = "/{customerId}")
   public void delete(@PathVariable(value = "tourId") int tourId,
                      @PathVariable(value = "customerId") int customerId) {
        TourRating tourRating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(tourRating);
   }

   private TourRating verifyTourRating(int tourId, int customerId) throws NoSuchElementException{
        return tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId, customerId)
                .orElseThrow(() -> new NoSuchElementException("Tour-Rating pair for request ("
                + tourId + " for customer" + customerId));
   }

    private Tour verifyTour(int tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId).orElseThrow(() ->
                new NoSuchElementException("Tour does not exist: " + tourId));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();
    }
}
