package com.apps;

public class Main {
    public static void main(String[] args) {

        {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}
