package com.linkedin.springboot.microservices.repository;

import com.linkedin.springboot.microservices.domain.TourPackage;
import org.springframework.data.repository.CrudRepository;

public interface TourPackageRepository extends CrudRepository<TourPackage, String> {
}
