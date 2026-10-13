package top.moningf.codehub.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.moningf.codehub.model.entity.User;
import top.moningf.codehub.properties.JwtProperties;
import top.moningf.codehub.utils.JwtUtil;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader(jwtProperties.getTokenName());
        // 1. 查看 TOKEN 是否存在
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // 2. 解析校验 TOKEN
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), authorization.substring(7));
            if(claims != null){
                // 3. 封装认证对象
                Long id = claims.get("id", Long.class);
                if (id == null) {
                    filterChain.doFilter(request, response);
                    return;
                }
                User user = new User() ;
                user.setId(id);
                user.setUsername(claims.get("username", String.class));
                user.setNickname(claims.get("nickname", String.class));
                // 4. 存入 SecurityContextHolder 上下文
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, List.of());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }catch (ExpiredJwtException e){
            SecurityContextHolder.clearContext();
            log.debug("JWT 已过期: {}", e.getMessage());
        }
        catch (Exception e) {
            SecurityContextHolder.clearContext();
            log.warn("JWT 解析失败: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}