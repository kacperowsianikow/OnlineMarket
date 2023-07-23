package org.example.dto;

import jakarta.validation.constraints.Email;
import org.example.validation.ValidPassword;

public record SignUpRequest(String firstName,
                            String lastName,
                            @Email
                            String email,
                            @ValidPassword
                            String password) {

}
