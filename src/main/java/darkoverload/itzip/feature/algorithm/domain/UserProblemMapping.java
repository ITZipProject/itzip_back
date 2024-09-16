package darkoverload.itzip.feature.algorithm.domain;

import darkoverload.itzip.feature.algorithm.entity.UserProblemMappingEntity;
import darkoverload.itzip.feature.algorithm.entity.embedded.UserProblemMappingId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProblemMapping {
    private UserProblemMappingId userProblemMappingId;
    private SolvedacUser solvedacUserEntity;
    private Problem problemEntity;

    public UserProblemMappingEntity convertToEntity() {
        return UserProblemMappingEntity.builder()
                .userProblemMappingId(this.userProblemMappingId)
                .solvedacUserEntity(this.solvedacUserEntity.convertToEntity())
                .problemEntity(this.problemEntity.convertToEntity())
                .build();
    }
}