package darkoverload.itzip.global.config.response.handler;

import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.response.handler.Util.ResponseHandlerUtil;
import darkoverload.itzip.global.config.response.response.ExceptionResponse;
import darkoverload.itzip.global.config.response.response.SuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

@RestControllerAdvice(basePackages = "darkoverload.itzip")
@Slf4j
public class GlobalSuccessHandler implements ResponseBodyAdvice<Object> {

    private static final Logger logger = LoggerFactory.getLogger(GlobalSuccessHandler.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        logger.info("body: {}", body);

        if (!(body instanceof SuccessResponse) && !(body instanceof String) && !(body instanceof ProblemDetail) && !(body instanceof ExceptionResponse)) {
            Object handledBody = ResponseHandlerUtil.handleSuccess(CommonResponseCode.SUCCESS, body).getBody();
            logger.info("body responseBody: {}", handledBody);
            return handledBody;
        }

        if (body instanceof String) {
            String handledBody = "{\"status\":\"200 OK\",\"msg\":\"요청이 성공됐습니다\",\"data\":\"" + body + "\"}";
            logger.info("string responseBody: {}", handledBody);
            return handledBody;
        }

        return body;
    }
}