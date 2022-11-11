package com.springBoot.microservices.repository;

import com.springBoot.microservices.model.TourPackage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "packages", path = "packages")
public interface TourPackageRepository extends CrudRepository<TourPackage, String> {

    Optional<TourPackage> findByName(@Param("name") String name);
}

