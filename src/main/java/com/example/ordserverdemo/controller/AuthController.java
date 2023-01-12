package com.example.ordserverdemo.controller;

import com.example.ordserverdemo.enitity.OTP;
import com.example.ordserverdemo.enitity.User;
import com.example.ordserverdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    //curl -X POST -H "content-type: application/json" -d '{"username":"john","password":"12345"}' http://localhost:8080/user/add
    @PostMapping("/user/add")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    //curl -X POST -H "content-type: application/json" -d '{"username":"john","password":"12345"}' http://localhost:8080/user/auth
    @PostMapping("/user/auth")
    public void auth(@RequestBody User user) {
        userService.auth(user);
    }

    //curl -v -X POST -H "content-type: application/json" -d '{"username":"john","code":"4654"}' http://localhost:8080/otp/check
    @PostMapping("/otp/check")
    public void check(@RequestBody OTP otp, HttpServletResponse response) {
        if (userService.check(otp)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
