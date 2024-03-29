package security.example.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.example.security.auth.dto.AuthenticationRequest;
import security.example.security.auth.dto.RegisterRequest;
import security.example.security.response.ResponseModel;
import security.example.security.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseModel register(@RequestBody RegisterRequest registerRequest){
        return authenticationService.register(registerRequest);
    }



    @PostMapping("/login")
    public ResponseModel login(@RequestBody AuthenticationRequest authenticationRequest){
        return authenticationService.authenticate(authenticationRequest);
    }

}
