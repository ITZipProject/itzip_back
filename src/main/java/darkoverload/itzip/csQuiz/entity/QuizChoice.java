package darkoverload.itzip.csQuiz.entity;

import lombok.Builder;
import lombok.Getter;

//퀴즈 정답을 담을 객체
@Getter
@Builder
public class QuizChoice {
    private Integer id;
    private String choiceText;

    public QuizChoice update(String choiceText) {
        return QuizChoice.builder()
                .id(this.id)
                .choiceText(choiceText)
                .build();
    }
}
