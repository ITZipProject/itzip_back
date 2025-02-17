package darkoverload.itzip.feature.csQuiz.entity;

import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Entity
@Table(name = "quiz_scores")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private Integer score;

    private QuizScore(final UserEntity user) {
        notNullParameters(user);
        this.user = user;
        this.score = 0;
    }

    private void notNullParameters(final UserEntity user) {
        if (Objects.isNull(user)) {
            throw new RestApiException(CommonExceptionCode.QUIZ_PROCESSING_ERROR);
        }
    }

    public static QuizScore create(final UserEntity user) {
        return new QuizScore(user);
    }

    public void incrementScore(final Integer score) {
        this.score += score;
    }

}
