package darkoverload.itzip.feature.csQuiz.controller.request;

import darkoverload.itzip.feature.csQuiz.entity.QuizChoice;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

//사용자가 생성한 문제를 받아올 객체
@Schema(description = "사용자가 생성한 문제를 받아올 객체")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizCreatedRequest {
    @NotBlank(message = "문제는 필수 입력 항목입니다.")
    @Schema(description = "문제", example = "다음중 가잠 깜찍한 백엔드는?")
    private String questionText;

    @Min(value = 1, message = "난이도는 최소 1이어야 합니다.")
    @Max(value = 3, message = "난이도는 최대 3이어야 합니다.")
    @Schema(description = "난이도", example = "3")
    private Integer difficulty;

    @NotNull(message = "정답 번호는 필수 입력 항목입니다.")
    @Schema(description = "문제번호 0부터 시작하면 된다.", example = "0")
    private Integer answer;

    @NotNull(message = "문제를 만든 사용자 ID는 필수 입력 항목입니다.")
    @Schema(description = "문제를 만든 사용자 ID", example = "5")
    private Long userId;

    @NotEmpty(message = "정답지 모음은 필수 입력 항목입니다.")
    @Schema(description = "정답지 모음")
    private List<QuizChoice> choices;

    @NotNull(message = "카테고리는 필수 입력 항목입니다.")
    @Schema(description = "카테고리 id값", example = "6")
    private Long categoryId;
}