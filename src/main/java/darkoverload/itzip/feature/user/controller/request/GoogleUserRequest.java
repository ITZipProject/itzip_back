package darkoverload.itzip.feature.user.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoogleUserRequest {
    @NotBlank(message = "구글 엑세스 토큰값을 입력해주세요.")
    @Schema(description = "구글 엑세스 토큰값", example = "ya29.a0ARW5m77UXvL8KtAR1OOB8g0ttMvCAvu123EeFhTwSyRpGvyXeiRka8NeLyzDxKzxAdv8wxwFWLnJ-CnwT_LvTUL3W1Tr4fPdgaxhGfLqGJgxAQyKrvIBb58V8L7jNh0ytUevDw5UaCw8-h4uXsHAszjETzelUZZWdoaCgYKAdwSARISFQHGX2Mi8ZZcBbzDMd0IVfG0rqG8eQ0170")
    public String accessToken;
}
