package darkoverload.itzip.user.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 이메일 발송 dto
 */
@Getter
@AllArgsConstructor
public class EmailSendRequest {
    @NotEmpty(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$",
            message = "올바르지 않은 이메일 형식입니다.")
    private String email;

    private String subject;

    private String text;
}
