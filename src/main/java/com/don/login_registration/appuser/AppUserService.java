package com.don.login_registration.appuser;

import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.don.login_registration.registration.token.ConfirmationToken;
import com.don.login_registration.registration.token.ConfirmationTokenService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * The Service class to hold business logic for the AppUser class
 * Implements the UserDetailsService interface for user authentication
 * The @service labels the class as a component for dependency injection
 * Uses the @AllArgsConstructor for constructor injection
 * The LoadUserByUsername method is overridden to load user by email
 **/
@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

    //the instance variables for the AppUserRepository, BCryptPasswordEncoder, and ConfirmationTokenService
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException
                                (String.format(USER_NOT_FOUND_MSG, email)));
    }

    public List<AppUser> getUsers() {
        return appUserRepository.findAll();
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository.findByEmail(appUser.getEmail())
                .isPresent();
        if (userExists) {
            AppUser existingUser = appUserRepository.findByEmail(appUser.getEmail()).get();
            if (!existingUser.getEnabled()) {
                confirmationTokenService.saveConfirmationToken(createToken(existingUser));
                throw new IllegalStateException("User already registered but not confirmed. Confirmation token resent.");
            }

            throw new IllegalStateException("email already taken");
        }


        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);
        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                null, appUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);


        return token;


    }

    public ConfirmationToken createToken(AppUser appUser) {
        String token = UUID.randomUUID().toString();
        return new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                null, appUser
        );
    }


    public void enableAppUser(String email) {
        appUserRepository.enableAppUser(email);
    }
}
