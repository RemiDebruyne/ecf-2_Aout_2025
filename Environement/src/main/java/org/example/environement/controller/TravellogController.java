package org.example.environement.controller;

import org.example.environement.dto.travellogs.TravellogDtoReceive;
import org.example.environement.dto.travellogs.TravellogDtoResponse;
import org.example.environement.dto.travellogs.TravellogDtoStat;
import org.example.environement.service.TravellogsService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/travellog")
public class TravellogController {

    private final TravellogsService travellogsService;

    public TravellogController(TravellogsService travellogsService) {
        this.travellogsService = travellogsService;
    }

    @GetMapping
    public ResponseEntity<List<TravellogDtoResponse>> getAllTravellogs(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(travellogsService.getAll(page, pageSize));
    }

    @GetMapping("/stats/{observationId}")
    public ResponseEntity<TravellogDtoStat> getStatFromObseration(@PathVariable long observationId) {
        return ResponseEntity.ok(travellogsService.getStats(observationId));
    }

    @GetMapping("/user/{name}")
    public ResponseEntity<Map<String, TravellogDtoStat>> getTravelStatForUserOnLAstMonth(@PathVariable String name) {
        return ResponseEntity.ok(travellogsService.getStatForUserLastMonth(name));
    }

    @PostMapping
    public ResponseEntity<TravellogDtoResponse> create(@RequestBody TravellogDtoReceive travellogDtoReceive) {
        TravellogDtoResponse travellogDtoResponse = travellogsService.add(travellogDtoReceive);

        return ResponseEntity.ok(travellogDtoResponse);
    }
}
