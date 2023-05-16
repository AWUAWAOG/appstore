package com.apps.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import java.sql.Date;

@Data
@Entity
@Table(name = "developers")
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "developers_id_seq_gen")
    @SequenceGenerator(name = "developers_id_seq_gen", sequenceName = "developers_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private int age;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "created")
    private Date created;

    @Column(name = "edited")
    private Date edited;
}