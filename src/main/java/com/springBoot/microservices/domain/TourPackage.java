package com.springBoot.microservices.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class TourPackage {

    @Id
    private String code;

    @Column
    private String name;

}
