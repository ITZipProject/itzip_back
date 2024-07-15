package darkoverload.itzip.global.config.response.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class ExceptionResponse {

    private final String status;
    private final String code;
    private final Object data;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationException> errors;

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationException {
        private final String status;
        private final String field;
        private final Object data;

        public static ValidationException of(final FieldError fieldError) {
            return ValidationException.builder()
                    .status("400 Bad Request")
                    .field(fieldError.getField())
                    .data(fieldError.getDefaultMessage())
                    .build();
        }
    }
}