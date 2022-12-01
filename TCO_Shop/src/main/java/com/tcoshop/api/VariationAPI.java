package com.tcoshop.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcoshop.entity.Variation;
import com.tcoshop.service.VariationService;

@RestController
@CrossOrigin("*")
public class VariationAPI {
    @Autowired
    VariationService variationService;

    @GetMapping("/api/variation/all")
    public ResponseEntity<List<Variation>> findAll() {
        return ResponseEntity.ok(variationService.findAll());
    }
    @PostMapping("/api/variation")
    public ResponseEntity<Variation> create(@RequestBody Variation variation) {
        return ResponseEntity.ok(variationService.create(variation));
    }
    @PutMapping("/api/variation")
    public ResponseEntity<Variation> update(@RequestBody Variation variation) {
        return ResponseEntity.ok(variationService.update(variation));
    }
    @DeleteMapping("/api/variation")
    public void delete(@RequestParam("id") String id) {
        variationService.delete(id);
    }
}
