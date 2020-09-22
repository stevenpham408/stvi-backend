package com.stvi.urlshortener.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrl {
    @Id
    private String hash;

    @NotEmpty @NotNull
    private String longUrl;

    private int userId;
}
