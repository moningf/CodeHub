package top.moningf.codehub.model.vo;

import lombok.Data;

@Data
public class LoginVo {
    private String token;
    public LoginVo(String token) {
        this.token = token;
    }
}
