package com.apps.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class RegistrationRequest {

    @Pattern(regexp = "[A-z], [0-9]")
    private String userLogin;

    @Size(min = 8, max = 24)
    private String userPassword;

    @Email
    private String email;
    private String firstName;
    private String lastName;
}
