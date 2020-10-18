package com.stvi.urlshortener.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="SHORT_URL")
public class ShortUrl {
    public ShortUrl(String hash, String longUrl, int userId){
        this.hash = hash;
        this.longUrl = longUrl;
        this.userId = userId;
    }

    @GeneratedValue
    @Id
    @JsonIgnore
    private int id;

    private String hash;
    private String longUrl;
    private int userId;
}
