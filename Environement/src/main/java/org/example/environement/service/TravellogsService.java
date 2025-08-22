package org.example.environement.service;

import org.example.environement.dto.observation.ObservationDtoResponse;
import org.example.environement.dto.travellogs.TravellogDtoReceive;
import org.example.environement.dto.travellogs.TravellogDtoResponse;
import org.example.environement.dto.travellogs.TravellogDtoStat;
import org.example.environement.entity.Observation;
import org.example.environement.entity.Travellog;
import org.example.environement.entity.enums.TravelMode;
import org.example.environement.exception.NotFoundException;
import org.example.environement.repository.ObservationRepository;
import org.example.environement.repository.TravellogRepository;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TravellogsService {
    private final TravellogRepository travellogRepository;
    private final ObservationRepository observationRepository;

    public TravellogsService(TravellogRepository travellogRepository, ObservationRepository observationRepository) {
        this.travellogRepository = travellogRepository;
        this.observationRepository = observationRepository;
    }

    public TravellogDtoResponse get(long id) {
        Travellog travellog = travellogRepository.findById(id);

        return travellog.entityToDto();
    }

    public List<TravellogDtoResponse> getAll(int page, int size) {

        return convertList(travellogRepository.findAll(PageRequest.of(page, size)).getContent());
    }


    public TravellogDtoStat getStats(long id) {
        List<Travellog> travellogs = travellogRepository.findTravellogByObservation_Id(id);

        TravellogDtoStat travellogDtoStat = new TravellogDtoStat();
        for (Travellog travellog : travellogs) {
            travellogDtoStat.addMode(travellog.getMode().toString(), travellog.getEstimatedCo2Kg());
            travellogDtoStat.addTotalEmissionsKg(travellog.getEstimatedCo2Kg());
            travellogDtoStat.addTotalDistanceKm(travellog.getDistanceKm());
        }


        return travellogDtoStat;
    }

    public Map<String, TravellogDtoStat> getStatForUserLastMonth(String user) {
        List<Travellog> travellogs = travellogRepository.findTravellogByUserForLastMonth(user, LocalDate.now().minusMonths(1));

        var travellogMap = new HashMap<String, TravellogDtoStat>();
        TravellogDtoStat travellogDtoStat;
        for (Travellog travellog : travellogs) {
            if (travellogMap.containsKey(travellog.getObservation().getObservationDate().toString())) {
                travellogDtoStat = travellogMap.get(travellog.getObservation().getObservationDate().toString());
            } else {
                travellogDtoStat = new TravellogDtoStat();
            }

            travellogDtoStat.addMode(travellog.getMode().toString(), travellog.getEstimatedCo2Kg());
            travellogDtoStat.addTotalDistanceKm(travellog.getDistanceKm());
            travellogDtoStat.addTotalEmissionsKg(travellog.getEstimatedCo2Kg());

            travellogMap.put(travellog.getObservation().getObservationDate().toString(), travellogDtoStat);
        }

        return travellogMap;
    }

    public TravellogDtoResponse add(TravellogDtoReceive travellogDtoReceive) {
        Travellog travellog = travellogDtoReceive.dtoToEntity(observationRepository);
        travellogRepository.save(travellog);

        return travellog.entityToDto();
    }

    private List<TravellogDtoResponse> convertList(List<Travellog> observations) {
        return observations.stream().map(Travellog::entityToDto).collect(Collectors.toList());
    }
}