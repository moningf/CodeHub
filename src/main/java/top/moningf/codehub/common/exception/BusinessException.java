package top.moningf.codehub.common.exception;

import lombok.Getter;
import top.moningf.codehub.common.enums.ErrorCode;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
