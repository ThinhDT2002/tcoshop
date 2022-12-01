package com.tcoshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcoshop.entity.Variation;
import com.tcoshop.repository.VariationRepository;
import com.tcoshop.service.VariationService;

@Service
public class VariationServiceImpl implements VariationService{
    @Autowired
    VariationRepository variationRepository;
    @Override
    public Variation create(Variation variation) {
        return variationRepository.save(variation);
    }
    @Override
    public List<Variation> findAll() {
        return variationRepository.findAll();
    }
    @Override
    public Variation update(Variation variation) {
        return variationRepository.save(variation);
    }
    @Override
    public Variation findById(String id) {
        return variationRepository.findById(id).get();
    }
    @Override
    public void delete(String id) {
        variationRepository.deleteById(id);
        
    }

}
