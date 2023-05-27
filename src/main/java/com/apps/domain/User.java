package com.apps.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.SequenceGenerator;
import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Data
@Entity
@Valid
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq_gen")
    @SequenceGenerator(name = "users_id_seq_gen", sequenceName = "users_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "user_login")
    @NotBlank
    private String userLogin;

    @Column(name = "user_password")
    @NotBlank
    private String userPassword;

    @Column(name = "email")
    @Email
    @NotBlank
    private String email;

    @Column(name = "first_name")
    @NotBlank
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    private String lastName;

    @Column(name = "created")
    private Date created;

    @Column(name = "edited")
    private Date edited;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "role")
    private String role;
}