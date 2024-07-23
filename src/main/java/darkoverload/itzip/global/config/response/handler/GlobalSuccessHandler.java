package darkoverload.itzip.global.config.response.handler;

import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.response.handler.Util.ResponseHandlerUtil;
import darkoverload.itzip.global.config.response.response.ExceptionResponse;
import darkoverload.itzip.global.config.response.response.SuccessResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

/*
basePackages를 통해서 어느 부분까지 응답을 강탈할껀지 설정
ResponseBodyAdvice를 사용했다.
 */
@RestControllerAdvice(basePackages = "darkoverload.itzip")
@Slf4j
public class GlobalSuccessHandler implements ResponseBodyAdvice<Object> {

    /**
     * 컨트롤러 메서드의 반환 유형과 변환기 유형(ex application/json)에 대해서 어느 것을 받을 건지 정하는 메서드이나 모든 컨트롤러를 받을것이기 떄문에 항상 true를 반환한다.
     * @param returnType    컨트롤러 메서드의 반환 유형
     * @param converterType 선택된 변환기 유형
     * @return              항상 ture를 반환하기 때문에 모든 응답에 적용됨
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * supports에서 ture가 반환되면 이 메서드가 실행됨
     * 상태코드가 200번대 일 경우에만 이방식으로 래핑되고 나머지는 ExceptionHandlerUtill로 넘김
     * @param body                  응답의 본문
     * @param returnType            컨트롤러 메서드의 반환 유형
     * @param selectedContentType   응답에 사용할 컨텐츠 유형
     * @param selectedConverterType 응답에 작성할 변환기 유형
     * @param request               현재 요청
     * @param response              현재 응답
     * @return                      상태 코드가 200번대일 경우 표준화된 응답 형식으로 래핑된 본문이 반환됨
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {

        HttpServletResponse servletResponse = ((ServletServerHttpResponse) response).getServletResponse();
        int statusCode = servletResponse.getStatus();

        if (statusCode >= 200 && statusCode < 300) {
            CommonResponseCode responseCode = getResponseCodeByStatus(statusCode);

            //성공응답 코드이고 String이 아니면 이 반환법으로 반환
            if (!(body instanceof SuccessResponse) && !(body instanceof String) && !(body instanceof ProblemDetail) && !(body instanceof ExceptionResponse)) {
                Object handledBody = ResponseHandlerUtil.handleSuccess(responseCode, body).getBody();
                return handledBody;
            }

            //문자열로 반환하는 컨트롤러라면 다음 형식을 따르도록함
            if (body instanceof String) {
                String handledBody = "{\"status\":\"200 OK\",\"msg\":\"요청이 성공됐습니다\",\"data\":\"" + body + "\",\"code\":\"SUCCESS\"}";
                return handledBody;
            }
        }

        return body;
    }

    /**
     * 상태 코드에 따라서 CommonResponseCode를 반환하게 됨
     * @param statusCode 상태 코드를 받아옴
     * @return           상태 코드에 해당하는 CommonResponseCode를 반환하거나 없으면 null값으로 반환)
     */
    private CommonResponseCode getResponseCodeByStatus(int statusCode) {
        for (CommonResponseCode code : CommonResponseCode.values()) {
            if (code.getHttpStatus().value() == statusCode) {
                return code;
            }
        }
        return null;
    }
}