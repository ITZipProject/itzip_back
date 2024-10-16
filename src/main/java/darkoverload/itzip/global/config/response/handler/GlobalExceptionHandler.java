package darkoverload.itzip.global.config.response.handler;

import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.handler.Util.ExceptionHandlerUtil;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.naming.ServiceUnavailableException;

/*
예외가 발생할시 반환을 가로채서 예외를 형식에 맞게 출력시키는 헨들러
 */
@RestControllerAdvice(basePackages = "darkoverload.itzip")
@Slf4j
public class GlobalExceptionHandler {

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

    // 요청 파라미터 검증 시 발생하는 예외를 처리하는 핸들러
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,
            MissingServletRequestPartException.class,
            HandlerMethodValidationException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<Object> handleValidationExceptions(Exception e) {
        log.error("{}: {}", e.getClass().getSimpleName(), e.getMessage(), e);
        return ExceptionHandlerUtil.handleExceptionInternal(CommonExceptionCode.FILED_ERROR);
    }

    // 요청 시간 초과 시 발생하는 예외를 처리하는 핸들러
    @ExceptionHandler(ClientAbortException.class)
    public ResponseEntity<Object> handleRequestTimeout(ClientAbortException e) {
        log.error("ClientAbortException: {}", e.getMessage(), e);
        return ExceptionHandlerUtil.handleExceptionInternal(CommonExceptionCode.REQUEST_TIMEOUT);
    }

    // 서버 과부하 유지보수 일 경우 발생하는 예외를 처리하는 핸들러
    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<Object> handleServiceUnavailable(ServiceUnavailableException e) {
        log.error("ServiceUnavailableException: {}", e.getMessage(), e);
        return ExceptionHandlerUtil.handleExceptionInternal(CommonExceptionCode.SERVICE_UNAVAILABLE);
    }
}