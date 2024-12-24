package darkoverload.itzip.feature.resume.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class ResumeInfoScrapRequest {

    @NotBlank(message = "이력서 id는 필수 입니다.")
    @Schema(description = "이력서 id")
    Long id;

    @NotBlank(message="유저 이메일은 필수 값입니다.")
    String email;
}
