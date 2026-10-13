package top.moningf.codehub.service;

import com.baomidou.mybatisplus.spring.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import top.moningf.codehub.model.dto.LoginRequest;
import top.moningf.codehub.model.dto.RegisterRequest;
import top.moningf.codehub.model.entity.User;
import top.moningf.codehub.model.vo.LoginVo;

public interface UserService extends IService<User> {

    Long register(RegisterRequest registerRequest);

    LoginVo login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response);
}
