package com.springBoot.microservices.service;

import com.springBoot.microservices.domain.Difficulty;
import com.springBoot.microservices.domain.Region;
import com.springBoot.microservices.domain.Tour;
import com.springBoot.microservices.domain.TourPackage;
import com.springBoot.microservices.repository.TourPackageRepository;
import com.springBoot.microservices.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Tour  Service
 *
 * Created by Mary Ellen Bowman
 */
@Service
public class TourService {
    private final TourRepository tourRepository;
    private final TourPackageRepository tourPackageRepository;

    @Autowired
    public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository) {
        this.tourRepository = tourRepository;
        this.tourPackageRepository = tourPackageRepository;
    }
    public Tour createTour(String title,
                           String description,
                           String blurb,
                           Integer price,
                           String duration,
                           String bullets,
                           String keywords,
                           String tourPackageName,
                           Difficulty difficulty,
                           Region region ) {

        TourPackage tourPackage = tourPackageRepository
                .findByName(tourPackageName)
                .orElseThrow( () -> new RuntimeException("Tour package does not exist: " + tourPackageName));

        return tourRepository
                .save(new Tour(title, description,blurb, price, duration, bullets, keywords, tourPackage, difficulty, region));
    }
    public long total() {
        return tourRepository.count();
    }
}

