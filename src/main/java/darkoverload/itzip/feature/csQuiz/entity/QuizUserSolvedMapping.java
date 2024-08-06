package darkoverload.itzip.feature.csQuiz.entity;

import darkoverload.itzip.feature.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//사용자가 푼 문제를 저장하는 엔티티
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz_user_solved_mapping")
public class QuizUserSolvedMapping{
    @Id
    private Long id;

    //사용자의 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    //사용자가 푼 문제 id
    @Column(name = "problem_id")
    private String problemId; // MongoDB id를 저장하는 필드

    //사용자가 문제를 푼 시간
    @Column(name = "timeStamp")
    private LocalDateTime timeStamp;

    //사용자가 문제에 준 점수
    @Column(name = "given_points")
    private Integer givenPoints;

    //사용자의 정답 여부
    @Column(name = "is_correct")
    private Boolean isCorrect;
}