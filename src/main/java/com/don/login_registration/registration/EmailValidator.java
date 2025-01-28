package com.don.login_registration.registration;


import org.springframework.stereotype.Service;

import java.util.function.Predicate;
/**
 * This is an email Validator
 * */
@Service
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String s) {
        String regex="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n";
        return s.matches(regex);
    }




}
