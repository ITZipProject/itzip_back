package darkoverload.itzip.feature.algorithm.controller.response;

import darkoverload.itzip.feature.algorithm.domain.ProblemTag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "테그 정보를 보내줄 응답")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolvedTagResponse {
    @Schema(description = "tag")
    List<ProblemTag> tags;
}
