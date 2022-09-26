package com.springBoot.microservices.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class Tour {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(length = 2000)
    private String blurb;

    @Column
    private Integer price;

    @Column
    private String duration;

    @Column(length = 2000)
    private String bullets;

    @Column
    private String keywords;

    @ManyToOne
    private TourPackage tourPackage;

    @Column
    @Enumerated
    private Difficulty difficulty;

    @Column
    @Enumerated
    private Region region;

    public Tour(String title,
                String description,
                String blurb,
                Integer price,
                String duration,
                String bullets,
                String keywords,
                TourPackage tourPackage,
                Difficulty difficulty,
                Region region) {
    }
}
