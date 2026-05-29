package com.airoport.backend.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.airoport.backend.dto.SolutionDTO;
import com.airoport.backend.model.Solution;
import com.airoport.backend.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(allowedOriginPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
@RestController
@RequestMapping("/api/solutions")
public class SolutionController {

    private final SolutionService solutionService;

    @Autowired
    public SolutionController(SolutionService solutionService) {
        this.solutionService = solutionService;
    }


    @GetMapping
    public ResponseEntity<List<Solution>> getAll() {
        return ResponseEntity.ok(solutionService.getdAll());
    }

    // 🔹 GET: Rechercher par nom
    @GetMapping("/search")
    public ResponseEntity<List<Solution>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(solutionService.getSolutionByName(name));
    }

    // 🔹 POST: Créer une nouvelle solution
    @PostMapping
    public ResponseEntity<Solution> create(@RequestBody SolutionDTO dto) {
        return ResponseEntity.ok(solutionService.createSolution(dto));
    }

    // 🔹 PUT: Mettre à jour une solution existante
    @PutMapping("/{id}")
    public ResponseEntity<Solution> update(@PathVariable int id, @RequestBody SolutionDTO dto) {
        return ResponseEntity.ok(solutionService.updateSolution(dto, id));
    }

    // 🔹 DELETE: Supprimer une solution par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        solutionService.deleteSolution(id);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/stats/date-range")
    public ResponseEntity<List<Solution>> getSolutionsByDateRange(
            @RequestParam String start,
            @RequestParam String end) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(start, formatter);
        LocalDateTime endDate = LocalDateTime.parse(end, formatter);

        List<Solution> solutions = solutionService.getSolutionsByDateRange(startDate, endDate);
        return ResponseEntity.ok(solutions);
    }

    @GetMapping("/stats/total")
    public ResponseEntity<Long> getTotalSolutions() {
        return ResponseEntity.ok(solutionService.getTotalSolutions());
    }



}

