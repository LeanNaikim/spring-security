package co.cstad.springsecurity.service;



import co.cstad.springsecurity.model.dto.UserRequest;
import co.cstad.springsecurity.model.dto.UserResponse;

import java.util.List;
public interface UserService {
    UserResponse createUser(UserRequest userRequest);

}
