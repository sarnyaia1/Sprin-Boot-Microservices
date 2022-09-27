package com.springBoot.microservices.web;

import com.springBoot.microservices.domain.Tour;
import com.springBoot.microservices.domain.TourRating;
import com.springBoot.microservices.domain.TourRatingPk;
import com.springBoot.microservices.repository.TourRatingRepository;
import com.springBoot.microservices.repository.TourRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@NoArgsConstructor
public class TourRatingController {

    TourRatingRepository tourRatingRepository;
    TourRepository tourRepository;

    @Autowired
    public TourRatingController(TourRatingRepository tourRatingRepository,  TourRepository tourRepository){
        this.tourRatingRepository = tourRatingRepository;
        this.tourRepository = tourRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTourRating(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto) {
        Tour tour = verifyTour(tourId);
        tourRatingRepository.save(new TourRating( new TourRatingPk(tour, ratingDto.getCustomerId()),
                ratingDto.getScore(), ratingDto.getComment()));
    }

    private Tour verifyTour(int tourId) throws NoSuchElementException {
        return tourRepository
                .findById(tourId)
                .orElseThrow( () -> new NoSuchElementException("Tour does not exist:" + tourId));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex){
        return ex.getMessage();
    }

//    @GetMapping
//    public List<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId){
//        verifyTour(tourId);
//        return tourRatingRepository.findByPkTourId(tourId)
//                                    .stream()
//                                    .map(RatingDto::new)
//                                    .collect(Collectors.toList());
//    }

    @GetMapping
    public Page<RatingDto> getAllRatingsForTour(@PathVariable(value = "tourId") int tourId, Pageable pageable){
        verifyTour(tourId);
        Page<TourRating> ratings = tourRatingRepository.findByPkTourId(tourId, pageable);
        return new PageImpl<>(ratings.get()
                .map(RatingDto::new)
                .collect(Collectors.toList()),pageable,ratings.getTotalElements()
        );
    }

    @GetMapping(path = "/average")
    public Map<String, Double> getAverage(@PathVariable(value = "tourId") int tourId){
        verifyTour(tourId);
        return Map.of("average", tourRatingRepository.findByPkTourId(tourId)
                .stream()
                .mapToInt(TourRating::getScore)
                .average()
                .orElseThrow( () -> new NoSuchElementException("Tour has no Ratings")));
    }

    @PutMapping
    public RatingDto updateWithPut(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto){
        TourRating rating = verifyTourRating(tourId, ratingDto.getCustomerId());
        rating.setScore(ratingDto.getScore());
        rating.setComment(ratingDto.getComment());
        return new RatingDto(tourRatingRepository.save(rating));
    }

    @PatchMapping
    public RatingDto updateWithPatch(@PathVariable(value = "tourId") int tourId, @RequestBody @Validated RatingDto ratingDto){
        TourRating rating = verifyTourRating(tourId, ratingDto.getCustomerId());
        if(ratingDto.getScore() != null){
            rating.setScore(ratingDto.getScore());
        }
        if(ratingDto.getComment() != null){
            rating.setComment(ratingDto.getComment());
        }
        return new RatingDto(tourRatingRepository.save(rating));
    }

    @DeleteMapping(path = "/{customerId}")
    public void delete(@PathVariable(value = "tourId") int tourId,@PathVariable(value = "customerId") int customerId){
        TourRating rating = verifyTourRating(tourId, customerId);
        tourRatingRepository.delete(rating);
    }

    private TourRating verifyTourRating(int tourId, int customerId) throws NoSuchElementException {
        return tourRatingRepository.findByPkTourIdAndPkCustomerId(tourId, customerId).orElseThrow(() ->
                new NoSuchElementException("Tour-Rating pair for request("
                        + tourId + " for customer" + customerId));
    }
}
