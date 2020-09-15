package com.stvi.urlshortener.repository;

import com.stvi.urlshortener.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findById(int id);


}
