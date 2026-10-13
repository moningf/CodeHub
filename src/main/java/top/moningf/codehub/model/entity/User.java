package top.moningf.codehub.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@TableName("user")
@NoArgsConstructor
public class User {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String phone;
    private String avatar;
    private String bio;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
