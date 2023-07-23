package org.example.service.user;

import org.example.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {
    List<GetUserAdminResponse> getAppUsers();
    String changePassword(Long id, String password);
    String updateUser(Long id, UpdateUserRequest updateUserRequest);
    GetUserResponse getUserData(Long id);
    String updateProfilePicture(Long id, MultipartFile file);
}
