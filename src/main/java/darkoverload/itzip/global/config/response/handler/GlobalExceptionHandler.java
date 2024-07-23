package darkoverload.itzip.global.config.response.handler;

import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.handler.Util.ExceptionHandlerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
예외가 발생할시 반환을 가로채서 예외를 형식에 맞게 출력시키는 헨들러
 */
@RestControllerAdvice(basePackages = "darkoverload.itzip")
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // RestApiException 예외를 처리하는 핸들러
    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Object> handleRestApiException(RestApiException e) {
        log.error("RestApiException: {}", e.getExceptionCode().getData(), e);
        return ExceptionHandlerUtil.handleExceptionInternal(e.getExceptionCode());
    }

    // 모든 예외를 처리하는 핸들러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception e) {
        log.error("Exception: {}", e.getMessage(), e);
        return ExceptionHandlerUtil.handleExceptionInternal(CommonExceptionCode.INTERNAL_SERVER_ERROR);
    }

    // 잘못된 인자가 전달될 때 발생하는 예외를 처리하는 핸들러
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException: {}", e.getMessage(), e);
        return ExceptionHandlerUtil.handleExceptionInternal(CommonExceptionCode.BAD_REQUEST, e.getMessage());
    }

    // 지원되지 않는 작업이 시도될 때 발생하는 예외를 처리하는 핸들러
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<Object> handleUnsupportedOperationException(UnsupportedOperationException e) {
        log.error("UnsupportedOperationException: {}", e.getMessage(), e);
        return ExceptionHandlerUtil.handleExceptionInternal(CommonExceptionCode.UNSUPPORTED_MEDIA_TYPE, e.getMessage());
    }


}