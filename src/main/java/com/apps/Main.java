package com.apps;

import org.springframework.boot.SpringApplication;

public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
