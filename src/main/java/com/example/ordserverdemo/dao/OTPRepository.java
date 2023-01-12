package com.example.ordserverdemo.dao;

import com.example.ordserverdemo.enitity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP,String> {

    Optional<OTP> findOTPByUsername(String username);
}
