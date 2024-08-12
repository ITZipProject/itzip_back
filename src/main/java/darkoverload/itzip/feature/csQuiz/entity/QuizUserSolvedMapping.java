package darkoverload.itzip.feature.csQuiz.entity;

import darkoverload.itzip.feature.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

//사용자가 푼 문제를 저장하는 엔티티
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz_user_solved_mapping")
public class QuizUserSolvedMapping{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //사용자의 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    //사용자가 푼 문제 id
    @Column(name = "_id", length = 50)
    private String problemId; // MongoDB id를 저장하는 필드

    //사용자가 문제를 푼 시간
    @Column(name = "time_stamp")
    private LocalDateTime timeStamp;

    //사용자가 문제에 준 점수
    @Column(name = "given_points")
    private Integer givenPoints;

    //사용자의 정답 여부
    @Column(name = "user_quiz_status", length = 10)
    @Enumerated(EnumType.STRING)
    private UserQuizStatus isCorrect;

    /**
     * 문제 아이디(`problemId`)를 기준으로 `QuizUserSolvedMapping` 객체의 동등성을 정의
     * 이를 통해 `HashSet`이나 `HashMap`에서 `QuizUserSolvedMapping` 객체를 고유하게 식별할 수 있도록 함
     */
    @Override
    public boolean equals(Object o) {
        // 동일한 객체인 경우 true 반환 (참조 동등성)
        if (this == o) return true;

        // 비교 대상 객체가 null이거나 클래스 타입이 다를 경우 false 반환
        if (o == null || getClass() != o.getClass()) return false;

        // 비교 대상 객체를 `QuizUserSolvedMapping` 타입으로 캐스팅
        QuizUserSolvedMapping that = (QuizUserSolvedMapping) o;

        // 두 객체의 `problemId`가 동일한지 비교
        return Objects.equals(problemId, that.problemId);
    }

    /**
     * 객체의 해시 코드를 `problemId`를 기준으로 생성
     * 동일한 `problemId`를 가진 객체는 동일한 해시 코드를 반환해서
     * 해시 기반 컬렉션(`HashSet`, `HashMap`)에서 올바르게 동작하도록 함
     */
    @Override
    public int hashCode() {
        // `problemId`를 기반으로 해시 코드를 생성
        return Objects.hash(problemId);
    }

    // 푼시간과 정답을 바꾸는 메서드
    public QuizUserSolvedMapping updateTimeStampAndIsCorrect(LocalDateTime timeStamp, UserQuizStatus isCorrect) {
        return new QuizUserSolvedMapping(this.id, this.user, this.problemId, timeStamp, this.givenPoints, isCorrect);
    }

    /**
     * 문제를 풀고나서 맞췄을때 점수를 줄때 mappingtable을 변경하는 메서드
     * @param givenPoints 준 점수
     * @return 준점수가 업데이트된 새로운 객체를 생성한다.
     */
    public QuizUserSolvedMapping updateGivenPoints(Integer givenPoints) {
        return new QuizUserSolvedMapping(this.id, this.user, this.problemId, this.timeStamp, givenPoints, this.isCorrect);
    }
}