package darkoverload.itzip.feature.algorithm.controller.response;

import darkoverload.itzip.feature.algorithm.domain.Problem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "사용자에게 추천해줄 문제를 담을 응답 dto")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemListResponse {
    @Schema(description = "사용자에게 추천해줄 문제가 담긴 리스트")
    List<Problem> problems;
}