package darkoverload.itzip.global.config.response.response;

import lombok.*;

/*
응답 형식을 제공하는 메서드
 */
@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class SuccessResponse<T> {
    // 응답 상태를 나타내는 필드
    private final String status;

    // 응답 메시지
    private final String msg;

    // 응답 데이터
    private final T data;

    // 응답 코드
    private final String code;
}