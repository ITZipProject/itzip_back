package darkoverload.itzip.feature.algorithm.domain;

import darkoverload.itzip.feature.algorithm.entity.UserProblemMappingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProblemMapping {
    private Long id;
    private SolvedacUser solvedacUserEntity;
    private Problem problemEntity;

    public UserProblemMappingEntity convertToEntity() {
        return UserProblemMappingEntity.builder()
                .id(this.id)
                .solvedacUserEntity(this.solvedacUserEntity.convertToEntity())
                .problemEntity(this.problemEntity.convertToEntity())
                .build();
    }
}