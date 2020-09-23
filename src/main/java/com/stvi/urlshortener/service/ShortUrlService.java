package com.stvi.urlshortener.service;

import com.google.common.hash.Hashing;
import com.stvi.urlshortener.controller.exceptions.UrlNotValidException;
import com.stvi.urlshortener.entity.ShortUrl;
import com.stvi.urlshortener.repository.ShortUrlRepository;
import com.stvi.urlshortener.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ShortUrlService {
    private final ShortUrlRepository shortUrlRepo;
    private final String pattern = "(([\\w]+:)?\\/\\/)?(([\\d\\w]|%[a-fA-f\\d]{2,2})+(:([\\d\\w]|%[a-fA-f\\d]{2,2})+)?@)?([\\d\\w][-\\d\\w]{0,253}[\\d\\w]\\.)+[\\w]{2,63}(:[\\d]+)?(\\/([-+_~.\\d\\w]|%[a-fA-f\\d]{2,2})*)*(\\?(&?([-+_~.\\d\\w]|%[a-fA-f\\d]{2,2})=?)*)?(#([-+_~.\\d\\w]|%[a-fA-f\\d]{2,2})*)?";

    public ShortUrl getShortUrlByLongUrl(String longUrl){
        return shortUrlRepo.findByLongUrl(longUrl);
    }

    public String getLongUrlByHash(String hash){
        return shortUrlRepo.findByHash(hash).getLongUrl();
    }

    public boolean doesLongUrlExist(String longUrl){ return shortUrlRepo.findByLongUrl(longUrl) != null; }

    public boolean doesShortUrlExist(String hash) { return shortUrlRepo.findByHash(hash) != null; }

    public ShortUrl makeShortUrl(String longUrl, int id){
        boolean isLongUrlValid = Pattern.compile(pattern).matcher(longUrl).find();

        // If the URL is not in the accepted formats, then we throw an error
        if(!isLongUrlValid){
            throw new UrlNotValidException();
        }

        // Append the id of the authenticated user making the request to the long URL
        String toHash = longUrl + id;

        // Hash the newly formed URL and save it to the database
        String shortUrl = Hashing.sha256().hashString(toHash, StandardCharsets.UTF_8).toString().substring(0, 7);
        return shortUrlRepo.save(new ShortUrl(shortUrl, longUrl, id));
    }
}