package darkoverload.itzip.global.config.response.exception;

import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.handler.Util.ExceptionHandlerUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//handler로 잡지 못하는 에러들을 처리하는 컨트롤러
@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<Object> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        //NoHandlerFoundException 예외처리
        if (status.equals(404)) {
            return ExceptionHandlerUtil.handleExceptionInternal(CommonExceptionCode.NOT_FOUND);
        }
        //HttpRequestMethodNotSupportedException 예외처리
        else if (status.equals(405)) {
            return ExceptionHandlerUtil.handleExceptionInternal(CommonExceptionCode.METHOD_NOT_ALLOWED);
        }

        //404, 405에도 안잡히면 예상치 못한 예외처리
        return ExceptionHandlerUtil.handleExceptionInternal(CommonExceptionCode.INTERNAL_SERVER_ERROR);
    }
}