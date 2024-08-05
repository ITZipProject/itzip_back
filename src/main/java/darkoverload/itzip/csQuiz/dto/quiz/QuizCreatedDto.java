package darkoverload.itzip.csQuiz.dto.quiz;

import darkoverload.itzip.csQuiz.entity.QuizChoice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//사용자가 생성한 문제를 받아올 객체
@Schema(description = "사용자가 생성한 문제를 받아올 객체")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizCreatedDto {
    @Schema(description = "문제", required = true, example = "다음중 가잠 깜찍한 백엔드는?")
    private String questionText;
    @Schema(description = "난이도", required = true, example = "3")
    private Integer difficulty;
    @Schema(description = "문제번호 0부터 시작하면 된다.", required = true, example = "0")
    private Integer answer;
    @Schema(description = "문제를 만든 사용자 ID", required = true, example = "10")
    private Long createUserId;
    @Schema(description = "정답지 모음", required = true)
    private List<QuizChoice> choices;
    @Schema(description = "카테고리 id값", required = true, example = "6")
    private Long categoryId;
}