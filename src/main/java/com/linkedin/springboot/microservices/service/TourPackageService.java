package com.linkedin.springboot.microservices.service;

import com.linkedin.springboot.microservices.domain.TourPackage;
import com.linkedin.springboot.microservices.repository.TourPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class TourPackageService {

    private final TourPackageRepository tourPackageRepository;

    @Autowired
    private TourPackageService(TourPackageRepository tourPackageRepository){
        this.tourPackageRepository = tourPackageRepository;
    }

    public TourPackage createTourPackage(String code, String name){
        return tourPackageRepository
                .findById(code)
                .orElse(tourPackageRepository.save(new TourPackage(code, name)));
    }

    public Iterable<TourPackage> lookUp(){
        return null;
    }

    public long total(){
        return 0;
    }
}
