package top.moningf.codehub.common.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.moningf.codehub.model.entity.User;

import java.util.Collection;
import java.util.List;

public class LoginUserDetails implements UserDetails {
    private User user;
    public User getUser(){
        return user;
    }

    public LoginUserDetails(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "LoginUserDetails{" +
                "user=" + user +
                '}';
    }

    /**
     * 获取用户权限信息
     *
     * @return
     */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * 获取用户密码
     *
     * @return
     */
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    /**
     * 获取用户账号
     *
     * @return
     */
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * 账号是否过期
     *
     * @return
     */
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    /**
     * 账号是否锁定
     *
     * @return
     */
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    /**
     * 密码是否过期
     *
     * @return
     */
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    /**
     * 账号是否可用
     *
     * @return
     */
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
