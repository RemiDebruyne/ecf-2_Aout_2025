package org.example.environement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.environement.dto.travellogs.TravellogDtoResponse;
import org.example.environement.entity.enums.TravelMode;
import org.example.environement.exception.TravelModeNotFoundException;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Travellog {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private double distanceKm;
    private double estimatedCo2Kg;
    private TravelMode mode;
    @ManyToOne
    @JoinColumn(name = "observation_id")
    private Observation observation;

    public TravellogDtoResponse entityToDto() {
        return TravellogDtoResponse.builder().distanceKm(this.distanceKm).estimatedCo2Kg(this.estimatedCo2Kg).mode(this.mode.toString()).build();
    }

    public void calculateCO2(double distanceKm, TravelMode mode) {
        switch (mode) {
            case TravelMode.WALKING:
            case TravelMode.BIKE:
                this.setEstimatedCo2Kg(0);
                break;
            case TravelMode.CAR:
                this.setEstimatedCo2Kg(distanceKm * 0.22);
                break;
            case TravelMode.BUS:
                this.setEstimatedCo2Kg(distanceKm * 0.11);
                break;
            case TravelMode.PLANE:
                this.setEstimatedCo2Kg(distanceKm * 0.03);
                break;
            case TravelMode.TRAIN:
                this.setEstimatedCo2Kg(distanceKm * 0.259);
                break;
            default:
                throw new TravelModeNotFoundException();
        }
    }
}
