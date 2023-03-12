package com.apps.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import java.sql.Date;

@Data
@Component
public class Users {
    private int id;
    private String user_login;
    private String user_password;

    @Email
    private String email;
    private String first_name;
    private String last_name;
    private Date created;
    private Date edited;
    private boolean is_deleted;
}
