package top.moningf.codehub.common.security;

import cn.hutool.json.JSONUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import top.moningf.codehub.common.response.Result;

import java.io.IOException;

@Component
public class LoginUnAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Result<String> result = Result.error(403, "权限不足");
        String jsonStr = JSONUtil.toJsonStr(result);
        response.getWriter().println(jsonStr);
    }
}
