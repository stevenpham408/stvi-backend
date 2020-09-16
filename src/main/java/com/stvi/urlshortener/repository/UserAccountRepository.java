package com.stvi.urlshortener.repository;

import com.stvi.urlshortener.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
    UserAccount findByUsername(String username);
    UserAccount findByEmail(String email);
    UserAccount findById(int id);


}
