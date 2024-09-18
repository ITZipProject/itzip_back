package darkoverload.itzip.feature.algorithm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import darkoverload.itzip.feature.algorithm.domain.Problem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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
    @Column(name = "problem_id")
    private Long problemId; // 문제 ID

    @Column(name = "title")
    private String title; // 문제 제목

    @Column(name = "level")
    private Integer level; // 문제 난이도

    @Column(name = "accepted_user_count")
    private Long acceptedUserCount; // 문제를 푼 유저 수

    @Column(name = "averageTries")
    private Integer averageTries; // 평균 시도 횟수

    //jpa 쿼리를 사용하기위한 매핑 2가지
    @OneToMany(mappedBy = "problemEntity", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<UserProblemMappingEntity> userProblemMappings;

    @OneToMany(mappedBy = "problemEntity", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ProblemTagMappingEntity> problemTagMappings = new HashSet<>();

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