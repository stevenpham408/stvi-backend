package com.stvi.urlshortener.repository;

import com.stvi.urlshortener.entity.ShortUrl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Integer> {
    ShortUrl findByHash(String hash);
    ShortUrl findByLongUrl(String longUrl);


    Page<ShortUrl> findByUserId(int userId, Pageable pageable);
    ShortUrl findByUserId(int userId);

    @Query("Select u FROM ShortUrl u WHERE u.longUrl = ?1 and u.userId = ?2 ")
    ShortUrl findLongUrlByUserId(String longUrl, int userId);
}