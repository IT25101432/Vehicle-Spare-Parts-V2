package com.spareparts.modules.auth.service;

import com.spareparts.core.dto.AuthResponse;
import com.spareparts.modules.auth.dto.LoginRequest;
import com.spareparts.modules.auth.dto.UserCreateRequest;
import com.spareparts.modules.auth.dto.UserResponse;
import com.spareparts.modules.auth.dto.UserUpdateRequest;
import com.spareparts.modules.auth.dto.ChangePasswordRequest;

import java.util.List;

public interface UserService {
    AuthResponse login(LoginRequest request);
    UserResponse createUser(UserCreateRequest request);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(Long id);
    UserResponse updateUser(Long id, UserUpdateRequest request);
    UserResponse toggleStatus(Long id);
    void deleteUser(Long id);

    void changePassword(String username, ChangePasswordRequest request);
}