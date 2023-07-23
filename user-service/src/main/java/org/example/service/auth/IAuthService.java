package org.example.service.auth;

import org.example.dto.SignUpRequest;

public interface IAuthService {
    String confirmToken(String token);
    String signUp(SignUpRequest signupRequest);
}
