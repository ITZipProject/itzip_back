package darkoverload.itzip.feature.csQuiz.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//퀴즈 카터고리를 받아올때 사용하는 객체
@Schema(description = "퀴즈 카테고리를 생성할때 보내주는 객체")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizCategoryFormRequest {
    @Schema(description = "카테고리 유형", required = true, example = "Itzip")
    private String categoryname;
}
