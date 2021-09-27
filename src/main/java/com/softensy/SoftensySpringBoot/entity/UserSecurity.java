package com.softensy.SoftensySpringBoot.entity;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public class UserSecurity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String login;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;

}
