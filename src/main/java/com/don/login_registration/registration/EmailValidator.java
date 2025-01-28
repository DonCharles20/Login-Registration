package com.don.login_registration.registration;


import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String s) {
        //Todo: regex to validate email
        return true;
    }

}
