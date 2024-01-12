package security.example.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import security.example.security.auth.dto.AuthenticationRequest;
import security.example.security.auth.dto.AuthenticationResponse;
import security.example.security.auth.dto.RegisterRequest;
import security.example.security.response.ResponseModel;
import security.example.security.entity.Role;
import security.example.security.entity.User;
import security.example.security.repository.RoleCustomRepo;
import security.example.security.repository.UserRepository;
import security.example.security.service.AuthenticationService;
import security.example.security.service.JwtService;
import security.example.security.service.UserService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RoleCustomRepo roleCustomRepo;
    private final UserService userService;
    private String errorMzg="Internal server error";

    @Override
    public ResponseModel register(RegisterRequest registerRequest) {
        ResponseModel responseModel =new ResponseModel();
        try {
            if (userRepository.existsById(registerRequest.getEmail())) {
                throw new IllegalArgumentException("User with " + registerRequest.getEmail() + "email already exists");
            }

            userService.saveUser(new User(registerRequest.getMobile_number(), registerRequest.getUser_name(), registerRequest.getEmail(), registerRequest.getPassword(), new HashSet<>()));
            userService.addToUser(registerRequest.getEmail(), "ROLE_USER");//default role
            User user = userRepository.findByEmail(registerRequest.getEmail()).orElseThrow();
            responseModel.setData(user);
            responseModel.setCode(200);
            responseModel.setMessage("Successfully added user");
            return responseModel;
        } catch (IllegalArgumentException e) {
            responseModel.setData((HttpStatus.BAD_REQUEST));
            return responseModel;
        } catch (Exception e) {
            responseModel.setCode(500);
            responseModel.setMessage(errorMzg);
            return responseModel;
        }
    }//mekma use krnna admin waltath

    @Override
    public ResponseModel registerAdmin(RegisterRequest registerRequest) {
        ResponseModel responseModel =new ResponseModel();
        try {
            if (userRepository.existsById(registerRequest.getEmail())) {
                throw new IllegalArgumentException("User with " + registerRequest.getEmail() + "email already exists");
            }

            userService.saveUser(new User(registerRequest.getMobile_number(), registerRequest.getUser_name(), registerRequest.getEmail(), registerRequest.getPassword(), new HashSet<>()));
            userService.addToUser(registerRequest.getEmail(), "ROLE_ADMIN");//I set this to admin
            User user = userRepository.findByEmail(registerRequest.getEmail()).orElseThrow();
            responseModel.setData(user);
            responseModel.setCode(200);
            responseModel.setMessage("Successfully added admin");
            return responseModel;
        } catch (IllegalArgumentException e) {
             responseModel.setData((HttpStatus.BAD_REQUEST));
             return responseModel;
        } catch (Exception e) {
            responseModel.setCode(500);
            responseModel.setMessage(errorMzg);
           return responseModel;
        }
    }

    @Override
    public ResponseModel authenticate(AuthenticationRequest authenticationRequest) {
        ResponseModel responseModel =new ResponseModel();
        try {
            User user = userRepository.findByEmail(authenticationRequest.getEmail())
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
            List<Role> role = roleCustomRepo.getRole(user);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Set<Role> set = new HashSet<>();
            role.stream().forEach(c -> {
                set.add(new Role(c.getName()));
                authorities.add(new SimpleGrantedAuthority(c.getName()));
            });
            user.setRoles(set);
            set.stream().forEach(i -> authorities.add(new SimpleGrantedAuthority(i.getName())));
            var jwtAccessToken = jwtService.generateToken(user, authorities);
            var jwtRefreshToken = jwtService.generateRefreshToken(user, authorities);

            AuthenticationResponse authenticationResponse=new AuthenticationResponse();
            authenticationResponse.setAccessToken(jwtAccessToken);
            authenticationResponse.setRefreshToken(jwtRefreshToken);
            authenticationResponse.setEmail(user.getEmail());
            authenticationResponse.setUserName(user.getUser_name());
            responseModel.setCode(200);
            responseModel.setData(authenticationResponse);
            responseModel.setMessage("Successfully login user");
            return responseModel;
        } catch (NoSuchElementException e) {
            responseModel.setCode(404);
          responseModel.setMessage("User not found");
            return responseModel;
        } catch (BadCredentialsException e) {
            responseModel.setCode(501);
            responseModel.setMessage("Invalid Password Credentials ");
            return responseModel;
        } catch (Exception e) {
            responseModel.setCode(500);
            responseModel.setData(e);
            return responseModel;
        }
    }

}
