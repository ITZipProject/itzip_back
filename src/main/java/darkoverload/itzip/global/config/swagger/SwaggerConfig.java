package darkoverload.itzip.global.config.swagger;

import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.response.response.SuccessResponse;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.media.Schema;
import lombok.SneakyThrows;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.Optional;

@Configuration
public class SwaggerConfig {

    @Bean
    public OperationCustomizer operationCustomizer() {
        return (operation, handlerMethod) -> {
            ResponseCodeAnnotation annotation = handlerMethod.getMethodAnnotation(ResponseCodeAnnotation.class);
            CommonResponseCode responseCode = Optional.ofNullable(annotation).map(ResponseCodeAnnotation::value).orElse(CommonResponseCode.SUCCESS);
            this.addResponseBodyWrapperSchema(operation, SuccessResponse.class, "data", responseCode);

            ExceptionCodeAnnotations exceptionAnnotations = handlerMethod.getMethodAnnotation(ExceptionCodeAnnotations.class);
            if (exceptionAnnotations != null) {
                for (CommonExceptionCode exceptionCode : exceptionAnnotations.value()) {
                    this.addExceptionResponseBodyWrapperSchemaExample(operation, exceptionCode);
                }
            }

            return operation;
        };
    }

    private void addResponseBodyWrapperSchema(Operation operation, Class<?> type, String wrapFieldName, CommonResponseCode responseCode) {
        ApiResponses responses = operation.getResponses();

        String responseCodeKey = String.valueOf(responseCode.getHttpStatus().value());

        if (!"200".equals(responseCodeKey)) {
            ApiResponse existingResponse = responses.get("200");
            if (existingResponse != null) {
                ApiResponse newResponse = new ApiResponse()
                        .description(existingResponse.getDescription())
                        .content(existingResponse.getContent());

                responses.addApiResponse(responseCodeKey, newResponse);
                responses.remove("200");
            }
        }

        ApiResponse response = responses.computeIfAbsent(String.valueOf(responseCode.getHttpStatus().value()), key -> new ApiResponse());
        response.setDescription(responseCode.getData());
        Content content = response.getContent();

        if (content != null) {
            content.keySet().forEach(mediaTypeKey -> {
                MediaType mediaType = content.get(mediaTypeKey);
                if (mediaType != null) {
                    mediaType.setSchema(wrapSchema(mediaType.getSchema(), type, wrapFieldName, responseCode));
                }
            });
        }
    }

    private void addExceptionResponseBodyWrapperSchemaExample(Operation operation, CommonExceptionCode exceptionCode) {
        ApiResponses responses = operation.getResponses();
        ApiResponse response = responses.computeIfAbsent(String.valueOf(exceptionCode.getHttpStatus().value()), key -> new ApiResponse());

        response.setDescription(exceptionCode.getData());
        Content content = response.getContent();
        if (content == null) {
            content = new Content();
            response.setContent(content);
        }

        MediaType mediaType = content.getOrDefault("application/json", new MediaType());
        mediaType.setSchema(createExceptionSchema(exceptionCode));
        content.addMediaType("application/json", mediaType);
        response.content(content);
        responses.addApiResponse(String.valueOf(exceptionCode.getHttpStatus().value()), response);
    }

    @SneakyThrows
    private <T> Schema<T> wrapSchema(Schema<?> originalSchema, Class<T> type, String wrapFieldName, CommonResponseCode responseCode) {
        Schema<T> wrapperSchema = new Schema<>();
        T instance = type.getDeclaredConstructor().newInstance();

        for (Field field : type.getDeclaredFields()) {
            field.setAccessible(true);
            Schema<?> fieldSchema = new Schema<>().example(field.get(instance));
            if (field.getName().equals("status")) {
                fieldSchema.example(responseCode.getHttpStatus().toString());
            } else if (field.getName().equals("msg")) {
                fieldSchema.example(responseCode.getData());
            } else if (field.getName().equals("data")) {
                fieldSchema = originalSchema;
            } else if (field.getName().equals("code")) {
                fieldSchema.example(responseCode.name());
            }
            wrapperSchema.addProperty(field.getName(), fieldSchema);
            field.setAccessible(false);
        }
        wrapperSchema.addProperty(wrapFieldName, originalSchema);
        return wrapperSchema;
    }

    private Schema<?> createExceptionSchema(CommonExceptionCode exceptionCode) {
        Schema<?> exceptionSchema = new Schema<>();
        exceptionSchema.addProperty("status", new Schema<>().example(exceptionCode.getHttpStatus().toString()));
        exceptionSchema.addProperty("code", new Schema<>().example(exceptionCode.name()));
        exceptionSchema.addProperty("data", new Schema<>().example(exceptionCode.getData()));
        return exceptionSchema;
    }
}