package com.authservices.authservicejune.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="name",nullable=false)
    private String name;

    @Column(name="username",nullable=false,unique=true)
    private String username;

    @Column(name="email",nullable=false,unique=true)
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="role",nullable=false)
    private String role;

    }
