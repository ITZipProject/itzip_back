package darkoverload.itzip.global.config.swagger;

import darkoverload.itzip.global.config.response.code.CommonExceptionCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
Target : 어노테이션 적용될 수 있는 위치 지금은 메서드로 설정
Retention : 어노테이션의 라이프 사이클 런타임 시에 유지되도록 설정
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionCodeAnnotations {
    //에러 코드는 기본 응답 값이 없음 but 여러개를 받을 수 있도록 설정
    CommonExceptionCode[] value();
}
