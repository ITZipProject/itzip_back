package darkoverload.itzip.feature.algorithm.entity;

import darkoverload.itzip.feature.algorithm.domain.UserProblemMapping;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//사용자가 푼문제를 연결할 매핑 테이블
@Entity
@Table(name = "user_problem_mapping")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserProblemMappingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 매핑 ID

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private SolvedacUserEntity solvedacUserEntity; // 유저 ID (외래 키)

    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false)
    private ProblemEntity problemEntity; // 문제 ID (외래 키)

    public UserProblemMapping convertToDomain() {
        return UserProblemMapping.builder()
                .id(this.id)
                .solvedacUserEntity(this.solvedacUserEntity.convertToDomain())
                .problemEntity(this.problemEntity.convertToDomain())
                .build();
    }
}