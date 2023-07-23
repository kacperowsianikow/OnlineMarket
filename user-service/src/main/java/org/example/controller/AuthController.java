package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.SignUpRequest;
import org.example.service.auth.IAuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/auth")
public class AuthController {
    private final IAuthService iAuthService;

    @GetMapping("/sign-up/confirm")
    public String confirmToken(@RequestParam(value = "token") String token) {
        return iAuthService.confirmToken(token);
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return iAuthService.signUp(signUpRequest);
    }

//    @PostMapping("/sign-in")
//    public JwtResponse signIn(@Valid @RequestBody SignInRequest signInRequest) {
//        return iAuthService.signIn(signInRequest);
//    }

}
