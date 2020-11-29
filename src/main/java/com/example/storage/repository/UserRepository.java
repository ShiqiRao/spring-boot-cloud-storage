package com.example.storage.repository;

import com.example.storage.model.TUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<TUser, Long> {

    TUser findByUsername(String username);

}
