package org.example.mapper;

import org.example.dto.SignUpRequest;
import org.example.dto.GetUserAdminResponse;
import org.example.dto.GetUserResponse;
import org.example.model.User;

public interface IUserMapper {
    User toUser(SignUpRequest signupRequest);
    GetUserAdminResponse toGetUserAdminResponseDto(User user);
    GetUserResponse toGetUserResponseDto(User user);

}
