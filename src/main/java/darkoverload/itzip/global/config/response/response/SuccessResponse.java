package darkoverload.itzip.global.config.response.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class SuccessResponse<T> {
    private final String status;
    private final String msg;
    private final T data;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final String code = "SUCCESS";
}