package darkoverload.itzip.global.config.swagger;

import io.swagger.v3.oas.annotations.media.Content;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//스웨거의 @RequestBody랑 spring의 anootation @RequestBody가 혼동되는 것을 막기 위해서 만든 커스텀 어노테이션 기존 @RequestBody대신 이걸 달아주면 된다.
//기존 스웨거의 @RequestBody랑 똑같은 기능을 수행한다.
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface SwaggerRequestBody {
    //리퀴스트 바디에 대한 설명
    String description() default "";

    //리퀘스트 바디가 필수인지 필수이면 ture 아니면 false
    boolean required() default true;

    //리퀘스트 바디의 미디어 타입을 지정 기존을 json으로 하도록 했음
    Content[] content() default @Content(mediaType = "application/json");
}