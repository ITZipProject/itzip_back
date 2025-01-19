package darkoverload.itzip.feature.user.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GithubUserRequest {
    @NotBlank(message = "깃허브 엑세스 토큰값을 입력해주세요.")
    @Schema(description = "깃허브 엑세스 토큰값", example = "gho_kEwNV1237NGFEyZsls8S7or3HzZGkm1huWce")
    public String accessToken;
}
