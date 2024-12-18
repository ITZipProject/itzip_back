package darkoverload.itzip.global.config.swagger;

import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.response.response.SuccessResponse;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

@Slf4j
@Configuration
@SecurityScheme(
        //보안 스키마 이름
        name = "Bearer Authentication",
        //인증 방식
        scheme = "bearer",
        //보안 유형
        type = SecuritySchemeType.HTTP,
        //Bearer Token의 형식 지정
        bearerFormat = "JWT"
)
public class SwaggerConfig {

    /**
     * Swagger의 Operation을 커스터마이징하는 빈을 생성
     *
     * @return 커스터마이징된 OperationCustomizer
     */
    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            // 메서드에 붙은 ResponseCodeAnnotation을 가져옴
            ResponseCodeAnnotation annotation = handlerMethod.getMethodAnnotation(ResponseCodeAnnotation.class);
            // 응답 코드를 설정, 기본값은 CommonResponseCode.SUCCESS
            CommonResponseCode responseCode = Optional.ofNullable(annotation).map(ResponseCodeAnnotation::value).orElse(CommonResponseCode.SUCCESS);
            // 응답 본문에 대한 래퍼 스키마를 추가
            this.addResponseBodyWrapperSchema(operation, SuccessResponse.class, "data", responseCode);

            // 메서드에 붙은 ExceptionCodeAnnotations를 가져옴
            ExceptionCodeAnnotations exceptionAnnotations = handlerMethod.getMethodAnnotation(ExceptionCodeAnnotations.class);
            if (exceptionAnnotations != null) {
                // 각 예외 코드를 응답에 추가
                for (CommonExceptionCode exceptionCode : exceptionAnnotations.value()) {
                    this.addExceptionResponse(operation, exceptionCode);
                }
            }

            // SwaggerRequestBody 커스텀 어노테이션 처리 추가
            // 메서드의 모든 파라미터를 반복하며 SwaggerRequestBody 어노테이션이 있는지 확인
            for (MethodParameter parameter : handlerMethod.getMethodParameters()) {
                SwaggerRequestBody swaggerRequestBody = parameter.getParameterAnnotation(SwaggerRequestBody.class);
                if (swaggerRequestBody != null) {
                    // 리퀘스트 바디 부분 문서화 추가
                    io.swagger.v3.oas.models.parameters.RequestBody requestBody = new io.swagger.v3.oas.models.parameters.RequestBody()
                            .description(swaggerRequestBody.description())
                            .required(swaggerRequestBody.required());

                    Content content = getContent(swaggerRequestBody);
                    requestBody.setContent(content);

                    operation.setRequestBody(requestBody);
                    break; // 필요한 어노테이션을 찾았으면 반복을 종료
                }
            }

            // Authorize 버튼을 통해 Bearer Authentication(jwt 헤더) 설정
            operation.addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"));

            return operation;
        };
    }

    /**
     * SwaggerRequestBody 어노테이션에서 content 정보를 추출하여 Content 객체를 생성
     *
     * @param swaggerRequestBody SwaggerRequestBody 어노테이션 인스턴스
     * @return Swagger에서 사용할 Content 객체
     */
    private static Content getContent(SwaggerRequestBody swaggerRequestBody) {
        Content content = new Content();
        MediaType mediaType = new MediaType();

        // @schema 스키마가 있을경우만 가져옴으로서 에러를 방지
        if (swaggerRequestBody.content().length > 0 && swaggerRequestBody.content()[0].schema() != null) {
            // 커스텀 어노테이션의 첫 번째 content에서 schema 설정 가져오기
            Schema<?> schema = new Schema<>();
            // Schema 구현 클래스를 반영
            Class<?> schemaImplementation = swaggerRequestBody.content()[0].schema().implementation();
            schema.set$ref(schemaImplementation.getSimpleName()); // 클래스 이름을 참고하도록 설정
            mediaType.setSchema(schema);
        }

        // 미디어 타입 지정을 안해줘도 알아서 적도록 변경
        content.addMediaType("application/json", mediaType);
        return content;
    }

    /**
     * 응답 본문에 대한 래퍼 스키마를 추가
     *
     * @param operation       Swagger의 Operation 객체
     * @param type            응답 타입 클래스
     * @param wrapFieldName   래핑할 필드 이름
     * @param responseCode    응답 코드
     */
    private void addResponseBodyWrapperSchema(Operation operation, Class<?> type, String wrapFieldName, CommonResponseCode responseCode) {
        ApiResponses responses = operation.getResponses();

        //응답 코드값 문자열로 변환
        String responseCodeKey = String.valueOf(responseCode.getHttpStatus().value());

        // 200 응답을 다른 응답 코드로 변경 200이아닐경우만
        // 200이 아닌 성공응답을 할때 기존 swagger에 보여지는 200값을 삭제해야함
        if (!"200".equals(responseCodeKey)) {
            ApiResponse existingResponse = responses.get("200");
            if (existingResponse != null) {
                // 기존 200 응답을 새로운 응답 코드로 변경
                ApiResponse newResponse = new ApiResponse()
                        .description(existingResponse.getDescription())
                        .content(existingResponse.getContent());

                // 새로운 응답 코드를 추가하고 기존 200 응답을 제거
                responses.addApiResponse(responseCodeKey, newResponse);
                responses.remove("200");
            }
        }

        // 응답 코드에 해당하는 ApiResponse 객체를 가져오거나 새로 만듬
        ApiResponse response = responses.computeIfAbsent(String.valueOf(responseCode.getHttpStatus().value()), key -> new ApiResponse());
        response.setDescription(responseCode.getData());
        Content content = response.getContent();

        // 응답 본문이 존재하는 경우, 각 미디어 타입에 대해 스키마를 래핑
        if (content != null) {
            content.keySet().forEach(mediaTypeKey -> {
                MediaType mediaType = content.get(mediaTypeKey);
                if (mediaType != null) {
                    // 기존 스키마를 래핑하는 스키마로 변경
                    mediaType.setSchema(wrapSchema(mediaType.getSchema(), type, wrapFieldName, responseCode));
                }
            });
        }
    }

    /**
     * 예외 응답 본문에 대한 래퍼 스키마 예제를 추가
     * 이 메서드는 특정 예외 코드에 대한 응답 스키마를 Swagger 문서에 추가
     * 동일한 HTTP 상태 코드로 여러 예외가 발생할 수 있는 경우 이를 처리하기 위해 예외 코드를 응답 스키마에 추가
     *
     * @param operation       Swagger의 Operation 객체
     * @param exceptionCode   예외 코드
     */
    private void addExceptionResponse(Operation operation, CommonExceptionCode exceptionCode) {
        ApiResponses responses = operation.getResponses();
        //응답 코드값 문자열로 변환
        String responseKey = String.valueOf(exceptionCode.getHttpStatus().value());

        // 새로운 ApiResponse 객체를 생성하고, 예외 코드의 설명을 설정합니다.
        ApiResponse response = new ApiResponse()
                .description(exceptionCode.getData());
        Content content = new Content();
        MediaType mediaType = new MediaType();
        mediaType.setSchema(createExceptionSchema(exceptionCode));
        content.addMediaType("application/json", mediaType);
        response.setContent(content);

        // 각 예외 코드를 고유한 키로 추가하여 중복되지 않도록 설정
        responses.addApiResponse(responseKey + "_" + exceptionCode.name(), response);
    }

    /**
     * 스키마를 래핑합니다.
     *
     * @param originalSchema  원본 스키마
     * @param type            응답 타입 클래스
     * @param wrapFieldName   래핑할 필드 이름
     * @param responseCode    응답 코드
     * @return 래핑된 스키마
     * @param <T>             제네릭 타입
     */
    @SneakyThrows
    private <T> Schema<T> wrapSchema(Schema<?> originalSchema, Class<T> type, String wrapFieldName, CommonResponseCode responseCode) {
        Schema<T> wrapperSchema = new Schema<>();
        T instance = type.getDeclaredConstructor().newInstance();

        // 래퍼 클래스의 각 필드를 스키마에 추가
        for (Field field : type.getDeclaredFields()) {
            String fieldName = field.getName();
            // 필드에 대응하는 getter 메서드 이름을 생성
            String getterName = "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            Method getterMethod = type.getMethod(getterName);
            // getter 메서드를 사용하여 필드 값을 가져옴
            Object value = getterMethod.invoke(instance);

            // 필드 값을 기반으로 새로운 스키마를 생성
            Schema<?> fieldSchema = new Schema<>().example(value);
            if (fieldName.equals("status")) {
                fieldSchema.example(responseCode.getHttpStatus().toString());
            } else if (fieldName.equals("msg")) {
                fieldSchema.example(responseCode.getData());
            } else if (fieldName.equals("data")) {
                fieldSchema = originalSchema;
            } else if (fieldName.equals("code")) {
                fieldSchema.example(responseCode.name());
            }
            // 생성된 필드 스키마를 래퍼 스키마에 추가
            wrapperSchema.addProperty(fieldName, fieldSchema);
        }
        // 원본 스키마를 래핑 필드로 추가
        wrapperSchema.addProperty(wrapFieldName, originalSchema);
        return wrapperSchema;
    }

    /**
     * 예외 스키마를 생성
     *
     * @param exceptionCode 예외 코드
     * @return 생성된 스키마
     */
    private Schema<?> createExceptionSchema(CommonExceptionCode exceptionCode) {
        // 새로운 스키마 객체를 생성
        Schema<?> exceptionSchema = new Schema<>();
        exceptionSchema.addProperty("status", new Schema<>().example(exceptionCode.getHttpStatus().toString()));
        exceptionSchema.addProperty("code", new Schema<>().example(exceptionCode.name()));
        exceptionSchema.addProperty("data", new Schema<>().example(exceptionCode.getData()));
        return exceptionSchema;
    }
}