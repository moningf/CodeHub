package top.moningf.codehub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.moningf.codehub.common.response.Result;
import top.moningf.codehub.model.entity.User;
import top.moningf.codehub.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * TODO 返回用户信息
     * @param authentication
     * @return
     */
    @GetMapping("/me")
    public Result<String> getProfile(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return Result.success( user.getNickname() + "已登录");
    }
}
