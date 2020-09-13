package com.stvi.urlshortener.entity;

import com.sun.istack.NotNull;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Value
@Entity
@Table(name="USER_TBL")
public class User {
    @Id
    @GeneratedValue
    int id;

    @NotNull @NotEmpty
    String username;

    @NotNull @NotEmpty
    String password;
    @NotNull @NotEmpty
    String matchingPassword;
    @NotNull @NotEmpty
    String email;
}
