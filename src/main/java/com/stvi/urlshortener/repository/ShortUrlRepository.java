package com.stvi.urlshortener.repository;

import com.stvi.urlshortener.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Integer> {
    ShortUrl findByHash(String hash);
    ShortUrl findByLongUrl(String longUrl);
    ShortUrl findByUserId(int userId);
}
