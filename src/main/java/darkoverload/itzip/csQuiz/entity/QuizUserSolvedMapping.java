package darkoverload.itzip.csQuiz.entity;

import darkoverload.itzip.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "quiz_user_solved_mapping")
public class QuizUserSolvedMapping {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "problem_id")
    private String problemId; // MongoDB id를 저장하는 필드

    @Column(name = "timeStamp")
    private LocalDateTime timeStamp;

    @Column(name = "given_points")
    private Integer givenPoints;

    @Column(name = "is_correct")
    private Boolean isCorrect;
}