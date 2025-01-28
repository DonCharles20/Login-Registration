package com.don.login_registration.registration;

import com.don.login_registration.appuser.AppUser;
import com.don.login_registration.appuser.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/registration")
public class RegistrationController {

    @Autowired
    private final AppUserService service;

    @Autowired
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
