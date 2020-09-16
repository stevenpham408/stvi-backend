package com.stvi.urlshortener.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="USER_TBL")
public class UserAccount {
    @Id
    @GeneratedValue
    private int id;

    @NotNull @NotEmpty
    private String username;

    @NotNull @NotEmpty
    private String password;

    @NotNull @NotEmpty
    private String email;
}
