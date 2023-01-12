package com.example.ordserverdemo.service;

import com.example.ordserverdemo.dao.OTPRepository;
import com.example.ordserverdemo.dao.UserRepository;
import com.example.ordserverdemo.enitity.OTP;
import com.example.ordserverdemo.enitity.User;
import com.example.ordserverdemo.util.GenerateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OTPRepository otpRepository;

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean check(OTP otpToValidate){
        Optional<OTP> userOTP = otpRepository.findOTPByUsername(otpToValidate.getUsername());
        if (userOTP.isPresent()){
            OTP otp = userOTP.get();
            if (otpToValidate.getCode().equals(otp.getCode())){
                return true;
            }
        }
        return false;
    }

    public void auth(User user) {
        Optional<User> userOptional = userRepository.findUserByUsername(user.getUsername());
        if (userOptional.isPresent()) {
            User u = userOptional.get();
            if (passwordEncoder.matches(user.getPassword(), u.getPassword())) {
                renewOTP(u);
            } else {
                throw new BadCredentialsException("Bad Credentials.");
            }
        } else {
            throw new BadCredentialsException("Bad Credentials.");
        }
    }

    private void renewOTP(User u) {
        String code = GenerateCodeUtil.generateCode();
        Optional<OTP> userOTP = otpRepository.findOTPByUsername(u.getUsername());
        if (userOTP.isPresent()) {
            OTP otp = userOTP.get();
            otp.setCode(code);
        } else {
            OTP otp = new OTP();
            otp.setUsername(u.getUsername());
            otp.setCode(code);
            otpRepository.save(otp);
        }
    }
}
