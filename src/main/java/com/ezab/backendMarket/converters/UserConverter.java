package com.ezab.backendMarket.converters;

import com.ezab.backendMarket.dtos.SignupRequestDTO;
import com.ezab.backendMarket.dtos.UserDTO;
import com.ezab.backendMarket.entities.User;

public class UserConverter extends AbstractConverter<User, UserDTO> {
    @Override
    public UserDTO fromEntity(User entity) {
        if (entity == null) return null;
        return UserDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .build();
    }

    @Override
    public User fromDTO(UserDTO dto) {
        if (dto == null) return null;
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .build();
    }

    public User signup(SignupRequestDTO requestDTO) {
        if (requestDTO == null) return null;
        return User.builder()
                .username(requestDTO.getUsername())
                .password(requestDTO.getPassword())
                .build();
    }
}
