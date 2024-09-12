package darkoverload.itzip.global.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.handler.Util.ExceptionHandlerUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * jwt 인증 실패 시 FORBIDDEN 예외 반환
 */
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 인증 실패 시 호출되는 메소드
     * @param request 클라이언트 요청 정보
     * @param response 클라이언트에게 반환할 응답 정보
     * @param authException 인증 예외 정보
     * @throws IOException 입출력 오류 발생 예외처리
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // 응답 콘텐츠 타입 JSON으로 설정
        response.setContentType("application/json");
        // 응답 문자 인코딩 utf-8로 설정
        response.setCharacterEncoding("utf-8");
        // 공통 예외처리 유틸 사용 및 응답 반환
        response.getWriter().write(new ObjectMapper().writeValueAsString(
                ExceptionHandlerUtil.handleExceptionInternal(CommonExceptionCode.FORBIDDEN).getBody()
        ));
    }
}