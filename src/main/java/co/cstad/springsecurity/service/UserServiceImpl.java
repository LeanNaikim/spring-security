package co.cstad.springsecurity.service;


import co.cstad.springsecurity.mapper.UserMapper;
import co.cstad.springsecurity.model.Role;
import co.cstad.springsecurity.model.User;
import co.cstad.springsecurity.model.dto.UserRequest;
import co.cstad.springsecurity.model.dto.UserResponse;
import co.cstad.springsecurity.repository.RoleRepository;
import co.cstad.springsecurity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;


    @Override
    public UserResponse createUser(UserRequest userRequest) {
        // check for email address
        if (userRepository.existsByEmail(userRequest.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
        }

        Set<Role> roles = new HashSet<>();
        // check for roles
        userRequest.roles().forEach(
                r -> {
                    var roleObject = roleRepository
                            .findByName(r)
                            .orElseThrow(() -> new ResponseStatusException(
                                    HttpStatus.BAD_REQUEST,
                                    "Role " + r + "not found"
                            ));
                    roles.add(roleObject);
                }
        );
        User newUser = userMapper.toUser(userRequest,roles);
        newUser.setPassword(new BCryptPasswordEncoder().encode(userRequest.password()));
        userRepository.save(newUser);
        return userMapper.toUserResponse(newUser);
    }
}