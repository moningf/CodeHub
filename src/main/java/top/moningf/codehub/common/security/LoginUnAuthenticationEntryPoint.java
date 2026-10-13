package top.moningf.codehub.common.security;

import cn.hutool.json.JSONUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.moningf.codehub.common.response.Result;

import java.io.IOException;

@Component
public class LoginUnAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Result<String> result = Result.error(401, "当前未登录，请登录后重试");
        String jsonStr = JSONUtil.toJsonStr(result);
        response.getWriter().println(jsonStr);
    }
}
