package com.linkedin.springboot.microservices.service;

import com.linkedin.springboot.microservices.domain.Difficulty;
import com.linkedin.springboot.microservices.domain.Region;
import com.linkedin.springboot.microservices.domain.Tour;
import com.linkedin.springboot.microservices.domain.TourPackage;
import com.linkedin.springboot.microservices.repository.TourPackageRepository;
import com.linkedin.springboot.microservices.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TourService {

    private final TourPackageRepository tourPackageRepository;
    private final TourRepository tourRepository;

    @Autowired
    public TourService(TourRepository tourRepository, TourPackageRepository tourPackageRepository){
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
                           Region region){

        TourPackage tourPackage = tourPackageRepository
                .findById(tourPackageName)
                .orElseThrow( () -> new RuntimeException("Tour package is not exist!"));

        return tourRepository
                .save(new Tour(title, description, blurb, price, duration, bullets, keywords, tourPackage, difficulty, region));
    }

    public long total(){
        return tourRepository.count();
    }
}
