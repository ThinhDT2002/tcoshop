package com.tcoshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcoshop.entity.Favorite;
import com.tcoshop.repository.FavoriteRepository;
import com.tcoshop.service.FavoriteService;
@Service
public class FavoriteServiceImpl implements FavoriteService{
    @Autowired
    FavoriteRepository favoriteRepository;
    @Override
    public Favorite create(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }
    @Override
    public List<Favorite> findByUsername(String username) {
        return favoriteRepository.findByUsername(username);
    }
    @Override
    public void delete(Integer favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }
    @Override
    public Favorite findByUsernameAndProductId(String username, Integer productId) {
        return favoriteRepository.findByUsernameAndProductId(username, productId);
    }
    
    
    
}
