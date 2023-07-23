package org.example.dto;

import java.util.List;

public record JwtResponse(Long id,
                          String token,
                          String email,
                          List<String> roles) {

}
