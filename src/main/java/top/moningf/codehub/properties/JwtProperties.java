package top.moningf.codehub.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Valid
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    /**
     * JWT 密钥
     */
    @NotBlank
    private String secretKey;
    /**
     * 过期时间，单位：毫秒
     */
    @Positive
    private long expirationTime;
    /**
     * JWT 请求头名称
     */
    private String tokenName = "Authorization";
}
