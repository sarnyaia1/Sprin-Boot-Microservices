package com.springBoot.microservices;

import com.springBoot.microservices.model.Difficulty;
import com.springBoot.microservices.model.Region;
import com.springBoot.microservices.service.TourPackageService;
import com.springBoot.microservices.service.TourService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.PropertyAccessor.FIELD;

@SpringBootApplication
@RequiredArgsConstructor
public class ExploreCaliforniaApplication implements CommandLineRunner {

    @Value("${microservices.importFile}")
    private String importFile;

    private final TourPackageService tourPackageService;

    private final TourService tourService;

    public static void main(String[] args) {
		SpringApplication.run(ExploreCaliforniaApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        createTourPackages();
        createTours(importFile);
    }

	private void createTourPackages(){
        tourPackageService.createTourPackage("BC", "Backpack Cal");
        tourPackageService.createTourPackage("CC", "California Calm");
        tourPackageService.createTourPackage("CH", "California Hot springs");
        tourPackageService.createTourPackage("CY", "Cycle California");
        tourPackageService.createTourPackage("DS", "From Desert to Sea");
        tourPackageService.createTourPackage("KC", "Kids California");
        tourPackageService.createTourPackage("NW", "Nature Watch");
        tourPackageService.createTourPackage("SC", "Snowboard Cali");
        tourPackageService.createTourPackage("TC", "Taste of California");
    }

    private void createTours(String fileToImport) throws IOException {
        TourFromFile.read(fileToImport).forEach(importedTour ->
            tourService.createTour(importedTour.getTitle(),
                    importedTour.getDescription(),
                    importedTour.getBlurb(),
                    importedTour.getPrice(),
                    importedTour.getDuration(),
                    importedTour.getBullets(),
                    importedTour.getKeywords(),
                    importedTour.getPackageType(),
                    importedTour.getDifficulty(),
                    importedTour.getRegion()));
    }

    @NoArgsConstructor
    @Getter
    private static class TourFromFile {

        private String packageType, title, description, blurb, price, duration, bullets, keywords, difficulty, region;

        static List<TourFromFile> read(String fileToImport) throws IOException {
            return new ObjectMapper().setVisibility(FIELD, ANY).
                    readValue(new FileInputStream(fileToImport), new TypeReference<List<TourFromFile>>() {});
        }

        Integer getPrice() { return Integer.parseInt(price); }

        Difficulty getDifficulty() { return Difficulty.valueOf(difficulty); }

        Region getRegion() { return Region.findByLabel(region); }
    }
}
