package darkoverload.itzip.csQuiz.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

//퀴즈 정답을 담을 객체
@Schema(description = "퀴즈 정답지을 담는 객체")
@Getter
@Builder
public class QuizChoice {
    @Schema(description = "퀴즈정답지 id값 0부터 시작", example = "0")
    private Integer id;
    @Schema(description = "퀴즈 정답지의 내용", example = "한김신박")
    private String choiceText;

    public QuizChoice update(String choiceText) {
        return QuizChoice.builder()
                .id(this.id)
                .choiceText(choiceText)
                .build();
    }
}