package top.moningf.codehub.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.sql.Date;
import java.util.Map;

public class JwtUtil {
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 指定加密算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 将字符串密钥转为 JJWT 安全的 Key 对象
        Key secretKeyBytes = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // 计算过期时间
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        // 构建 JWT
        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)                     // 设置自定义载荷
                .signWith(secretKeyBytes, signatureAlgorithm) // 签名加密
                .setExpiration(exp);                   // 设置过期时间

        return builder.compact();
    }

    public static Claims parseJWT(String secretKey, String jwt) {
        // 将字符串密钥转为 JJWT 安全的 Key 对象
        Key secretKeyBytes = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // 解析 JWT
        return Jwts.parserBuilder()
                .setSigningKey(secretKeyBytes) // 设置签名密钥
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
}
