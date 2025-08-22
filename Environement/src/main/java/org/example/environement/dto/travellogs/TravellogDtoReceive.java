package org.example.environement.dto.travellogs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.environement.entity.Travellog;
import org.example.environement.entity.enums.TravelMode;
import org.example.environement.exception.NotFoundException;
import org.example.environement.repository.ObservationRepository;
import org.example.environement.service.ObservationService;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TravellogDtoReceive {
    private double distanceKm;
    private String mode;
    private Long observationId;


    public Travellog dtoToEntity (ObservationRepository observationRepository){
        Travellog travellog = Travellog.builder()
                .distanceKm(this.getDistanceKm())
                .mode(TravelMode.valueOf(this.getMode()))
//                .observation(observationRepository.findById(observationId).orElseThrow(NotFoundException::new))
                .build();

        if(observationId != null){
            travellog.setObservation(observationRepository.findById(observationId).orElseThrow(NotFoundException::new));
        }

        travellog.calculateCO2(this.distanceKm, travellog.getMode());
        return travellog;
    }
}
