package darkoverload.itzip.global.config.swagger;

import darkoverload.itzip.global.config.response.code.CommonResponseCode;

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
public @interface ResponseCodeAnnotation {
    //응답 어노테이션의 기본값은 SUCCESS가 되도록 설정 어노테이션 아무것도 정하지 않으면 200 응답으로 처리됨
    CommonResponseCode value() default CommonResponseCode.SUCCESS;
}
