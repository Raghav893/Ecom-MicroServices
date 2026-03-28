package com.raghav.userservice.dto;

import com.raghav.userservice.Entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterDTO {
    @NotNull
    @Email
    private String emailId;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    private Role role

}
