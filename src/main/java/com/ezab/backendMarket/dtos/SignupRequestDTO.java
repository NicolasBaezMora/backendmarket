package com.ezab.backendMarket.dtos;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDTO {

    private String username;
    private String password;

}
