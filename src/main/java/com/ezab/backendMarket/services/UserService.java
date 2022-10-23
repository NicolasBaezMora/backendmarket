package com.ezab.backendMarket.services;

import com.ezab.backendMarket.converters.UserConverter;
import com.ezab.backendMarket.dtos.LoginRequestDTO;
import com.ezab.backendMarket.dtos.LoginResponseDTO;
import com.ezab.backendMarket.entities.User;
import com.ezab.backendMarket.exceptions.NoDataFoundException;
import com.ezab.backendMarket.exceptions.ValidateServiceException;
import com.ezab.backendMarket.helpers.TokenService;
import com.ezab.backendMarket.repositories.UserRepository;
import com.ezab.backendMarket.validators.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public User createUser(User user) {
        try {
            UserValidator.signup(user);
            User existUser = userRepository.findByUsername(user.getUsername())
                    .orElse(null);

            if (existUser != null)
                throw new ValidateServiceException("The username: " + user.getUsername() + " is already in use");

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            return userRepository.save(user);

        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }


    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        try {

            User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                    .orElseThrow(() -> new ValidateServiceException("Username or password invalid"));

            //if (!user.getPassword().equals(loginRequestDTO.getPassword()))
            if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword()))
                throw new ValidateServiceException("Username or password invalid");

            String token = tokenService.createToken(user);

            return LoginResponseDTO.builder()
                    .user(userConverter.fromEntity(user))
                    .token(token)
                    .build();


        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }



}

