package top.moningf.codehub.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.moningf.codehub.common.response.Result;
import top.moningf.codehub.model.dto.LoginRequest;
import top.moningf.codehub.model.dto.RegisterRequest;
import top.moningf.codehub.service.UserService;
import top.moningf.codehub.model.vo.LoginVo;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Result<Long> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return Result.success(userService.register(registerRequest));
    }

    @PostMapping("/login")
    public Result<LoginVo> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        return Result.success(userService.login(loginRequest,request,response));
    }
}
