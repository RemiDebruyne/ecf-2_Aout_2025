package org.example.environement.controller;

import org.example.environement.dto.observation.ObservationDtoReceive;
import org.example.environement.dto.observation.ObservationDtoResponse;
import org.example.environement.service.ObservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/observations")
public class ObservationController {

    private final ObservationService observationService;

    public ObservationController(ObservationService observationService) {
        this.observationService = observationService;
    }

    @GetMapping
    public ResponseEntity<List<ObservationDtoResponse>> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        List<ObservationDtoResponse> observationDtoResponses = observationService.get(pageSize, page);

        return ResponseEntity.ok(observationDtoResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObservationDtoResponse> getById(@PathVariable Integer id) {
        ObservationDtoResponse observationDtoResponse = observationService.get(id);
        return ResponseEntity.ok(observationDtoResponse);
    }

    @GetMapping("/by-location")
    public ResponseEntity<List<ObservationDtoResponse>> getByLocation(@RequestParam String location) {
        List<ObservationDtoResponse> observationDtoResponses = observationService.getByLocation(location);

        return ResponseEntity.ok(observationDtoResponses);
    }

    @GetMapping("/by-species/{speciesId}")
    public ResponseEntity<List<ObservationDtoResponse>> getBySpecies(@PathVariable Long speciesId) {
        List<ObservationDtoResponse> observationDtoResponses = observationService.getBySpecie(speciesId);

        return ResponseEntity.ok(observationDtoResponses);
    }

    @PostMapping
    public ResponseEntity<ObservationDtoResponse> create(@RequestBody ObservationDtoReceive observationDtoReceive){
        ObservationDtoResponse observationDtoResponse = observationService.create(observationDtoReceive);

        return ResponseEntity.ok(observationDtoResponse);
    }



}
