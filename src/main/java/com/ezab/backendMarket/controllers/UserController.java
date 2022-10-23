package com.ezab.backendMarket.controllers;

import com.ezab.backendMarket.converters.UserConverter;
import com.ezab.backendMarket.dtos.LoginRequestDTO;
import com.ezab.backendMarket.dtos.LoginResponseDTO;
import com.ezab.backendMarket.dtos.SignupRequestDTO;
import com.ezab.backendMarket.dtos.UserDTO;
import com.ezab.backendMarket.entities.User;
import com.ezab.backendMarket.services.UserService;
import com.ezab.backendMarket.utils.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;


    @PostMapping(value = "/signup")
    public ResponseEntity<WrapperResponse<UserDTO>> signup(
            @RequestBody SignupRequestDTO requestDTO
    ) {

        User user = userService.createUser(userConverter.signup(requestDTO));
        return new WrapperResponse<>(
                true,
                "Success",
                userConverter.fromEntity(user)
        ).createResponse();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<WrapperResponse<LoginResponseDTO>> login(
            @RequestBody LoginRequestDTO requestDTO
    ) {
        LoginResponseDTO response = userService.login(requestDTO);
        return new WrapperResponse<>(
                true,
                "Success",
                response
        ).createResponse();
    }

}
