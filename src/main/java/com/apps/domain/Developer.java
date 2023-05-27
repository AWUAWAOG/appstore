package com.apps.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Data
@Entity
@Valid
@Table(name = "developers")
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "developers_id_seq_gen")
    @SequenceGenerator(name = "developers_id_seq_gen", sequenceName = "developers_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "first_name")
    @NotBlank
    private String firstName;

    @Column(name = "last_name")
    @NotBlank
    private String lastName;

    @Column(name = "age")
    @NotBlank
    private int age;

    @Column(name = "birth_date")
    @NotBlank
    private Date birthDate;

    @Column(name = "created")
    private Date created;

    @Column(name = "edited")
    private Date edited;
}