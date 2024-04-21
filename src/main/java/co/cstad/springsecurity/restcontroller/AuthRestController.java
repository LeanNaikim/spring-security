package co.cstad.springsecurity.restcontroller;

import co.cstad.springsecurity.model.dto.UserRequest;
import co.cstad.springsecurity.model.dto.UserResponse;
import co.cstad.springsecurity.service.UserService;
import co.cstad.springsecurity.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthRestController {
    private final UserService userService;


    @PostMapping("/register")
    public BaseResponse<UserResponse> createNewUser(@RequestBody UserRequest userRequest) {
        return BaseResponse.<UserResponse>createSuccess()
                .setPayload(userService.createUser(userRequest));
    }

}