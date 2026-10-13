package top.moningf.codehub.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.moningf.codehub.common.enums.ErrorCode;
import top.moningf.codehub.common.exception.BusinessException;
import top.moningf.codehub.common.security.LoginUserDetails;
import top.moningf.codehub.mapper.UserMapper;
import top.moningf.codehub.model.dto.LoginRequest;
import top.moningf.codehub.model.dto.RegisterRequest;
import top.moningf.codehub.model.entity.User;
import top.moningf.codehub.model.vo.LoginVo;
import top.moningf.codehub.properties.JwtProperties;
import top.moningf.codehub.service.UserService;
import top.moningf.codehub.utils.JwtUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Long register(RegisterRequest registerRequest) {
        lambdaQuery().eq(User::getEmail, registerRequest.getEmail()).oneOpt().ifPresent(user -> {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        });

        String randomUsername = "user_" + UUID.randomUUID().toString().substring(0, 8);
        String emailPrefix = registerRequest.getEmail().split("@")[0];

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setUsername(randomUsername);
        user.setNickname(emailPrefix);
        save(user);
        return user.getId();
    }

    @Override
    public LoginVo login(LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);
            User user = ((LoginUserDetails) authenticate.getPrincipal()).getUser();
            // 生成 JWT token
            long expireTime = System.currentTimeMillis() + jwtProperties.getExpirationTime();
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", user.getId());
            claims.put("username", user.getUsername());
            claims.put("nickname", user.getNickname());
            String token = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getExpirationTime(), claims);
            return new LoginVo(token);
        } catch (BadCredentialsException exception) {
            throw new BusinessException(ErrorCode.LOGIN_ERROR);
        }
    }

}
