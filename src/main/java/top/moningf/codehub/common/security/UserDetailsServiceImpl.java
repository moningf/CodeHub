package top.moningf.codehub.common.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.moningf.codehub.mapper.UserMapper;
import top.moningf.codehub.model.entity.User;
import top.moningf.codehub.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getEmail, username);
//        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery().eq(User::getUsername, username);
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        if (user == null) {
            System.out.printf("User not found with username: %s%n", username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new LoginUserDetails(user);
    }
}
