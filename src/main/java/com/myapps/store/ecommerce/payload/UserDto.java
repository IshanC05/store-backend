package com.myapps.store.ecommerce.payload;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDto {

    private int userId;

    @Size(min = 3, message = "Name must be at-least 3 chars long")
    @NotNull(message = "Name cannot be null")
    private String name;

    @Email(message = "Email address is not valid")
    @NotNull(message = "Email cannot be null")
    @Size(min = 5, message = "Invalid Email address")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 5, message = "Password must be at-least 5 chars long")
    private String password;

    private String address;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone Number must be exactly 10 digits")
    @NotNull(message = "Phone Number cannot be empty")
    @NotEmpty(message = "Phone Number cannot be empty")
    private String phone;
    private Date date;
    private boolean active;

}
