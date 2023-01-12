package com.example.ordserverdemo.enitity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class User {

    @Id
    private String username;
    private String password;
}
