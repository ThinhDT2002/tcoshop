package com.tcoshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tcoshop.entity.Favorite;
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer>{
    @Query("select f from Favorite f where f.user.username=?1")
    List<Favorite> findByUsername(String username);
    @Query("select f from Favorite f where f.user.username=?1 and f.product.id=?2")
    Favorite findByUsernameAndProductId(String username, Integer productId);
}
