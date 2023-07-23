package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.service.user.IUserService;
import org.example.validation.ValidPassword;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final IUserService iUserService;

    @GetMapping("/list-users")
    public List<GetUserAdminResponse> getUsers() {
        return iUserService.getAppUsers();
    }

    @GetMapping("/{id}")
    public GetUserResponse getUserData(@RequestParam("id") Long id) {
        return iUserService.getUserData(id);
    }

    @PostMapping("/{id}/upload-file")
    public String uploadProfilePicture(@RequestParam("id") Long id,
                                       @RequestParam("file") MultipartFile file) {
        return iUserService.updateProfilePicture(id, file);
    }

    @PatchMapping("/{id}/change-password")
    public String changePassword(@RequestParam("id") Long id,
                                 @Valid @ValidPassword @RequestParam(value = "password") String password) {
        return iUserService.changePassword(id, password);
    }

    @PatchMapping("/{id}/update")
    public String updateUser(@RequestParam("id") Long id,
                             @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        return iUserService.updateUser(id, updateUserRequest);
    }

}
