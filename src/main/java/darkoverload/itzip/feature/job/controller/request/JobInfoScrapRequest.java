package darkoverload.itzip.feature.job.controller.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@ToString
@Getter
@AllArgsConstructor
public class JobInfoScrapRequest {


    // itzip 채용공고 id
    @NotBlank(message="채용공고 id는 필수 입니다.")
    @Schema(description = "채용공고 id")
    Long id;

    // userId
    @NotBlank(message="유저 이메일은 필수 값 입니다.")
    @Schema(description = "이메일")
    String email;

}
