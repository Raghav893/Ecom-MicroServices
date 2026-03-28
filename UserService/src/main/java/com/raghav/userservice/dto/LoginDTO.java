package com.raghav.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDTO {
    @NotNull
    @Email
    private String emailId;

    @NotNull
    private String password;
}
