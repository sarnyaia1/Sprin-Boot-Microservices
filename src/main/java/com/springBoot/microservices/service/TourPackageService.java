package com.springBoot.microservices.service;

import com.springBoot.microservices.model.TourPackage;
import com.springBoot.microservices.repository.TourPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TourPackageService {

    private final TourPackageRepository tourPackageRepository;

    public TourPackage createTourPackage(String code, String name) {
        return tourPackageRepository.findById(code).orElse(tourPackageRepository.save(new TourPackage(code, name)));
    }

    public Iterable<TourPackage> lookup(){
        return tourPackageRepository.findAll();
    }

    public long total() {
        return tourPackageRepository.count();
    }
}

