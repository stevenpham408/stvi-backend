package com.stvi.urlshortener.entity;

import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name="USER_TBL")
public class UserAccount {
    public UserAccount(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }
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