package com.apps.domain;

import lombok.Data;
import org.springframework.stereotype.Component;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@Component
public class User {
    private int id;

    @Pattern(regexp = "[A-z], [0-9]")
    @Size(min = 6, max = 15)
    private String user_login;

    @Size(min = 8, max = 18)
    private String user_password;

    @Email
    private String email;
    private String first_name;
    private String last_name;
    private Date created;
    private Date edited;
    private boolean is_deleted;
}
