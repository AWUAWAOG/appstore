package com.apps.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Data
@Component
public class Developer {
    private int id;
    private String first_name;
    private String last_name;
    private int age;
    private Date birth_date;
}
