package com.kras.exploremicroservice.controller;

import com.kras.exploremicroservice.model.Tour;
import com.kras.exploremicroservice.model.TourRating;
import com.kras.exploremicroservice.repo.TourRatingRepository;
import com.kras.exploremicroservice.repo.TourRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.NoSuchElementException;

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
   public void createTourRating(@PathVariable(value = "tourId") String tourId,
                                @RequestBody @Validated TourRating tourRating) {
        Tour tour = verifyTour(tourId);
        tourRatingRepository.save(new TourRating(tourId, tourRating.getCustomerId(),
                tourRating.getScore(), tourRating.getComment()));
   }

   @GetMapping
   public Page<TourRating> getAllRatingsForTour(@PathVariable(value = "tourId") String tourId,
                                               Pageable pageable) {
        return tourRatingRepository.findByTourId(tourId, pageable);
   }

   @GetMapping(path = "/average")
   public Map<String, Double> getAverage(@PathVariable(value = "tourId") String tourId) {
        verifyTour(tourId);
        return Map.of(AVERAGE, tourRatingRepository.findByTourId(tourId).stream()
                .mapToLong(TourRating::getScore)
                .average()
                .orElseThrow(() -> new NoSuchElementException("Tour has no Ratings")));
   }

   @PutMapping
   public TourRating updateWithPut(@PathVariable(value = "tourId") String tourId,
                                  @RequestBody @Validated TourRating tourRating) {
        TourRating rating = verifyTourRating(tourId, tourRating.getCustomerId());
        rating.setScore(tourRating.getScore());
        rating.setComment(tourRating.getComment());
        return tourRatingRepository.save(rating);
   }

   @PatchMapping
   public  TourRating updateWithPatch(@PathVariable(value = "tourId") String tourId,
                                     @RequestBody @Validated TourRating tourRating) {
       TourRating rating = verifyTourRating(tourId, tourRating.getCustomerId());
       if (tourRating.getScore() != null) {
           rating.setScore(tourRating.getScore());
       }
       if (tourRating.getComment() != null) {
           rating.setComment(tourRating.getComment());
       }
       return tourRatingRepository.save(rating);
   }

   @DeleteMapping(path = "/{customerId}")
   public void delete(@PathVariable(value = "tourId") String tourId,
                      @PathVariable(value = "customerId") int customerId) {
        TourRating tourRating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(tourRating);
   }

   private TourRating verifyTourRating(String tourId, int customerId) throws NoSuchElementException{
        return tourRatingRepository.findByTourIdAndCustomerId(tourId, customerId)
                .orElseThrow(() -> new NoSuchElementException("Tour-Rating pair for request ("
                + tourId + " for customer" + customerId));
   }

    private Tour verifyTour(String tourId) throws NoSuchElementException {
        return tourRepository.findById(tourId).orElseThrow(() ->
                new NoSuchElementException("Tour does not exist: " + tourId));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        return ex.getMessage();
    }
}
