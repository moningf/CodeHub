package top.moningf.codehub.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import top.moningf.codehub.common.enums.ErrorCode;
import top.moningf.codehub.common.response.Result;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidationException(MethodArgumentNotValidException exception) {
        String errorMessage = exception.getBindingResult().getFieldError().getDefaultMessage();
        return Result.error(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }

    @ExceptionHandler(BusinessException.class)
    public Result<Void> handleBusinessException(BusinessException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return Result.error(errorCode.getCode(), errorCode.getMessage());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Result<Void>> handleNoResourceFoundException(NoResourceFoundException exception) {
        log.error("资源未找到异常", exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Result.error(HttpStatus.NOT_FOUND.value(), "资源未找到"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Void>> handleUnexpected(Exception exception) {
        log.error("服务器发生未预期异常", exception);
        return ResponseEntity
                .internalServerError()
                .body(Result.error(500, ""));
    }

}
