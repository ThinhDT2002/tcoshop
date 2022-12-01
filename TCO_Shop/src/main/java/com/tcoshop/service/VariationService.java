package com.tcoshop.service;

import java.util.List;

import com.tcoshop.entity.Variation;

public interface VariationService {
    Variation create(Variation variation);
    List<Variation> findAll();
    Variation findById(String id);
    Variation update(Variation variation);
    void delete(String id);
}
