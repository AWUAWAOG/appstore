package com.apps.domain;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope
public class Application {
    private int id;
    private String app_name;
    private String app_category;
    private double rating;
    private String description;
    private int app_year;
    private double price;
}
