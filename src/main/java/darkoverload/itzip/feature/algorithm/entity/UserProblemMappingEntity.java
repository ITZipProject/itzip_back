package darkoverload.itzip.feature.algorithm.entity;

import darkoverload.itzip.feature.algorithm.domain.UserProblemMapping;
import darkoverload.itzip.feature.algorithm.entity.embedded.UserProblemMappingId;
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
    @EmbeddedId
    private UserProblemMappingId userProblemMappingId;

    @ManyToOne
    @MapsId("userId")  // 복합 키의 userId와 연결
    @JoinColumn(name = "user_id", nullable = false)
    private SolvedacUserEntity solvedacUserEntity;

    @ManyToOne
    @MapsId("problemId")  // 복합 키의 problemId와 연결
    @JoinColumn(name = "problem_id", nullable = false)
    private ProblemEntity problemEntity;

    public UserProblemMapping convertToDomain() {
        return UserProblemMapping.builder()
                .userProblemMappingId(this.userProblemMappingId)
                .solvedacUserEntity(this.solvedacUserEntity.convertToDomain())
                .problemEntity(this.problemEntity.convertToDomain())
                .build();
    }
}