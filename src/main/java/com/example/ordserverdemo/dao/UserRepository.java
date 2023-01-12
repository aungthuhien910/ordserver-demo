package com.example.ordserverdemo.dao;

import com.example.ordserverdemo.enitity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findUserByUsername(String username);
}
