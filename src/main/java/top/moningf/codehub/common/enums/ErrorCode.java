package top.moningf.codehub.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    EMAIL_ALREADY_EXISTS(10001, "邮箱已被注册"),
    ARTICLE_NOT_FOUND(20001, "文章不存在"),
    CATEGORY_NOT_FOUND(30001, "分类不存在"),
    LOGIN_ERROR(40001, "用户名或密码错误");

    private final int code;
    private final String message;
}
