package com.airoport.backend.controller;

import com.airoport.backend.dto.AeroportDTO;

import com.airoport.backend.model.Aeroport;

import com.airoport.backend.service.AeroportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(allowedOriginPatterns = {"http://localhost:*", "http://127.0.0.1:*"})
@RestController
@RequestMapping("/api/aeroports")
public class AeroportController {


    private final AeroportService aeroportService;

    @Autowired
    public AeroportController(AeroportService aeroportService) {
        this.aeroportService = aeroportService;
    }

    // 🔹 GET: Tous les Aeroport
    @GetMapping
    public ResponseEntity<List<Aeroport>> getAll() {
        return ResponseEntity.ok(aeroportService.getdAll());
    }

    // 🔹 GET: Rechercher par nom
    @GetMapping("/search")
    public ResponseEntity<List<Aeroport>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(aeroportService.getAeroportByIName(name));
    }

    // 🔹 POST: Créer une nouvelle Aeroport
    @PostMapping
    public ResponseEntity<Aeroport> create(@RequestBody AeroportDTO dto) {
        return ResponseEntity.ok(aeroportService.createAeroport(dto));
    }

    // 🔹 PUT: Mettre à jour une Aeroport existante
    @PutMapping("/{id}")
    public ResponseEntity<Aeroport> update(@PathVariable int id, @RequestBody AeroportDTO dto) {
        return ResponseEntity.ok(aeroportService.updateAeroport(dto, id));
    }

    // 🔹 DELETE: Supprimer une Aeroport par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        aeroportService.deleteAeroport(id);
        return ResponseEntity.noContent().build();
    }
}

