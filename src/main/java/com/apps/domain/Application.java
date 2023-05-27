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
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "applications_id_seq_gen")
    @SequenceGenerator(name = "applications_id_seq_gen", sequenceName = "applications_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "app_name")
    @NotBlank
    private String appName;

    @Column(name = "app_category")
    @NotBlank
    private String appCategory;

    @Column(name = "rating")
    @NotBlank
    private double rating;

    @Column(name = "description")
    private String description;

    @Column(name = "app_year")
    @NotBlank
    private int appYear;

    @Column(name = "price")
    @NotBlank
    private double price;

    @Column(name = "created")
    private Date created;

    @Column(name = "edited")
    private Date edited;
}