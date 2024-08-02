package darkoverload.itzip.csQuiz.dto.quiz;

import darkoverload.itzip.csQuiz.dto.quizcategory.QuizCategoryDetailDto;
import darkoverload.itzip.csQuiz.entity.QuizChoice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

//사용자에게 문제에 대한 정보를 줄때 사용하는 DTO
@Schema(description = "퀴즈 디테일 정보 담은 객체")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizDetailDto {
    @Schema(description = "문제 식별값", example = "1a2b3c4d5e6f7g8h9i0j")
    private String id;
    @Schema(description = "문제", example = "다음중 가잠 깜찍한 백엔드는?")
    private String questionText;
    @Schema(description = "난이도", example = "3")
    private Integer difficulty;
    @Schema(description = "카테고리 종류", example = "예시 문제 유형")
    private QuizCategoryDetailDto category;
    @Schema(description = "맞춘 유저 수", example = "9999")
    private Integer acceptedUserCount;
    @Schema(description = "시도한 유저 수", example = "10000")
    private Integer triedUserCount;
    @Schema(description = "맞춘 비율 % 소수점 2자리수까지 보여줌", example = "99.99")
    private double correctRate;
    @Schema(description = "사용자들이 평가함 점수", example = "100")
    private Integer points;
    @Schema(description = "검색을 시도한 사용자가 풀었는지 나타내는 값", example = "False")
    private Boolean userSolved;
    @Schema(description = "정답지 모음")
    private List<QuizChoice> choices;
}
