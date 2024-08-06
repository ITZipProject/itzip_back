package darkoverload.itzip.feature.csQuiz.dto.quizcategory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//퀴즈 카테고리를 저장하고 사용자한테 보내줄때 사용하는 객체
@Schema(description = "퀴즈 카테고리 정보를 보내주는 객체")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizCategoryDetailDto {
    @Schema(description = "카테고리 id", example = "5")
    private int id;
    @Schema(description = "카테고리 유형", example = "Itzip")
    private String categoryname;
}
