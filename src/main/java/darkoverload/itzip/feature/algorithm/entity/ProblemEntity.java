package darkoverload.itzip.feature.algorithm.entity;

import darkoverload.itzip.feature.algorithm.domain.Problem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//solvedac문제를 저장할 엔티티
@Entity
@Table(name = "problems")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long problemId; // 문제 ID

    private String title; // 문제 제목

    private Integer level; // 문제 난이도

    @Column(name = "accepted_user_count")
    private Integer acceptedUserCount; // 문제를 푼 유저 수

    private Integer averageTries; // 평균 시도 횟수

    public Problem convertToDomain() {
        return Problem.builder()
                .problemId(this.problemId)
                .title(this.title)
                .level(this.level)
                .acceptedUserCount(this.acceptedUserCount)
                .averageTries(this.averageTries)
                .build();
    }
}