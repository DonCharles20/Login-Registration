package com.don.login_registration.registration;

import com.don.login_registration.appuser.AppUser;
import com.don.login_registration.appuser.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * This class is a controller class that handles the registration of a user
 * It is a REST controller that listens to the /api/v1/registration endpoint
 * It uses the @RestController annotation to mark it as a controller class and
 * @RequestMapping annotation to map the endpoint to the class
 * uses two instance variables to hold the AppUserService and RegistrationService
 * */
@RestController
@RequestMapping("api/v1/registration")
public class RegistrationController {

    private final AppUserService service;
    private final RegistrationService serviceRequest;
    public RegistrationController(AppUserService service, RegistrationService serviceRequest) {
        this.service = service;
        this.serviceRequest = serviceRequest;
    }


    @GetMapping
    public List<AppUser> getUsers(){
        return service.getUsers();
    }

    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        return serviceRequest.register(request);
    }

    @PostMapping("/confirm")
    public String confirm(@RequestHeader("Authorization") String authorizationHeader) {
        // Extract the token from the "Bearer <token>" format
        String token = authorizationHeader.replace("Bearer ", "").trim();
        return serviceRequest.confirmToken(token);
    }
    
    @GetMapping("/confirm")
    public String confirmGet(@RequestParam("token") String token) {
        return serviceRequest.confirmToken(token);
    }


}
