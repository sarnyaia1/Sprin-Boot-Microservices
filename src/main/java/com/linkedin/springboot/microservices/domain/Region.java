package com.linkedin.springboot.microservices.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Region {

    Central_Coast("Central Coast"),
    Southern_California("Southern California"),
    Northern_California("Northern California"),
    Varies("Varies");

    private final String label;

    public static Region findByLabel(String byLabel){
        for(Region region : Region.values()){
            if(region.label.equalsIgnoreCase(byLabel)){
                return region;
            }
        }
        return null;
    }
}
