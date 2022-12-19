package com.tcoshop.service;

import java.util.List;

import com.tcoshop.entity.Favorite;

public interface FavoriteService {
    Favorite create(Favorite favorite);
    List<Favorite> findByUsername(String username);
    void delete(Integer favoriteId);
    Favorite findByUsernameAndProductId(String username, Integer productId);
}
