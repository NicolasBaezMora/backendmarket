package com.ezab.backendMarket.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {

    private UserDTO user;
    private String token;

}
