package security.example.security.service;


import security.example.security.auth.dto.AuthenticationRequest;
import security.example.security.auth.dto.RegisterRequest;
import security.example.security.response.ResponseModel;

public interface AuthenticationService {
    ResponseModel register(RegisterRequest registerRequest);
    ResponseModel registerAdmin(RegisterRequest registerRequest);
   ResponseModel authenticate(AuthenticationRequest authenticationRequest);
}
