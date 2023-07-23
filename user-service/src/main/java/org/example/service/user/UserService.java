package org.example.service.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.config.PasswordEncoderConfig;
import org.example.dto.GetUserAdminResponse;
import org.example.dto.GetUserResponse;
import org.example.dto.UpdateUserRequest;
import org.example.mapper.IUserMapper;
import org.example.model.User;
import org.example.repository.IUserRepository;
import org.example.service.file.StorageException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository iUserRepository;
    private final IUserMapper iUserMapper;
    private final PasswordEncoderConfig passwordEncoderConfig;

    @Override
    public List<GetUserAdminResponse> getAppUsers() {
        return iUserRepository.findAll().stream()
                .map(iUserMapper::toGetUserAdminResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public GetUserResponse getUserData(Long id) {
        return iUserRepository
                .findById(id)
                .stream()
                .map(iUserMapper::toGetUserResponseDto)
                .findFirst()
                .orElseThrow(
                        () -> new IllegalStateException("Error occurred while retrieving user data")
                );
    }

    @Override
    public String updateProfilePicture(Long id, MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("Cannot load empty file");
        }

        try {
            byte[] profilePicture = file.getBytes();

            User user = iUserRepository
                    .findById(id)
                    .orElseThrow(() -> new IllegalStateException("Unable to find user"));

            user.setProfilePicture(profilePicture);

            iUserRepository.save(user);
        } catch (IOException e) {
            throw new StorageException("An error occurred while loading file.", e);
        }

        return "Profile picture upload";
    }

    @Override
    @Transactional
    public String changePassword(Long id, String password) {
        String oldPasswordHash =
                iUserRepository.findById(id).orElseThrow().getPassword();
        if (passwordEncoderConfig.bCryptPasswordEncoder().matches(
                password,
                oldPasswordHash
        )) {
            throw new IllegalStateException(
                    "New password cannot be the same as the new one"
            );
        }

        iUserRepository.updateUserPassword(
                id,
                passwordEncoderConfig.bCryptPasswordEncoder().encode(password)
        );

        return "Changed password";
    }

    @Override
    public String updateUser(Long id, UpdateUserRequest updateUserRequest) {
        iUserRepository.updateUser(id, updateUserRequest);

        return "Your personal info is up to date";
    }

}
