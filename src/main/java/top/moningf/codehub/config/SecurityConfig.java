package top.moningf.codehub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.moningf.codehub.common.security.JwtAuthenticationTokenFilter;
import top.moningf.codehub.common.security.LoginUnAccessDeniedHandler;
import top.moningf.codehub.common.security.LoginUnAuthenticationEntryPoint;

@Configuration
public class SecurityConfig {

    @Autowired
    private LoginUnAuthenticationEntryPoint loginUnAuthenticationEntryPoint;
    @Autowired
    private LoginUnAccessDeniedHandler loginUnAccessDeniedHandler;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        httpSecurity
                .csrf(csrf -> csrf.disable())  // 禁用 CSRF 保护
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)    // 配置会话管理策略
                )
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth  // 配置请求授权规则
                        .requestMatchers(
                                "/api/auth/register",
                                "/api/auth/login"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception ->{   // 配置异常处理器
                    exception
                            // 未登录/token过期
                            .authenticationEntryPoint(loginUnAuthenticationEntryPoint)
                            // 权限不足
                            .accessDeniedHandler(loginUnAccessDeniedHandler);  // 配置权限访问被拒绝的处理器
                })
        ;
        return httpSecurity.build();
    }

    @Bean
    public FilterRegistrationBean<JwtAuthenticationTokenFilter> tenantFilterRegistration(JwtAuthenticationTokenFilter filter) {
        FilterRegistrationBean<JwtAuthenticationTokenFilter> registration = new FilterRegistrationBean<>(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
