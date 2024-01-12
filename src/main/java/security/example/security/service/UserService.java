package security.example.security.service;

import security.example.security.entity.Role;
import security.example.security.entity.User;

public interface UserService {
    
    User saveUser(User user);
    Role saveRole(Role role);
    
    void addToUser(String email,String rolename);
}
