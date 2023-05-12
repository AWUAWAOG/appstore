package com.apps.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq_gen")
    @SequenceGenerator(name = "users_id_seq_gen", sequenceName = "users_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "user_login")
    @Pattern(regexp = "[A-z], [0-9]")
    @Size(min = 5, max = 15)
    private String userLogin;

    @Column(name = "user_password")
    @Size(min = 8, max = 24)
    private String userPassword;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
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
