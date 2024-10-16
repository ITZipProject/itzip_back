package darkoverload.itzip.feature.algorithm.controller.response;

import darkoverload.itzip.feature.algorithm.domain.SolvedacUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "사용자 정보를 담은 응답 dto")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolvedUserResponse {
    @Schema(description = "사용자 객체")
    SolvedacUser solvedacUser;
}
