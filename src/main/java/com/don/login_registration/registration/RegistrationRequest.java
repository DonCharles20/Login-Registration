package com.don.login_registration.registration;


import lombok.Data;


@Data
public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    
    RegistrationRequest(){
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.password = "";
    }


}
