package org.example.mapper;

import org.example.dto.SignUpRequest;
import org.example.dto.GetUserAdminResponse;
import org.example.dto.GetUserResponse;
import org.example.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements IUserMapper {

    @Override
    public User toUser(SignUpRequest signupRequest) {
        return new User(
                signupRequest.firstName(),
                signupRequest.lastName(),
                signupRequest.email(),
                signupRequest.password()
        );
    }

    @Override
    public GetUserAdminResponse toGetUserAdminResponseDto(User user) {
        return new GetUserAdminResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRoles(),
                user.getCreatedAt(),
                user.getEnabledAt(),
                user.getLastUpdatedAt(),
                user.getIsEnabled()
        );
    }

    @Override
    public GetUserResponse toGetUserResponseDto(User user) {
        return new GetUserResponse(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getCity(),
                user.getCountry(),
                user.getZipCode()
        );
    }

}
