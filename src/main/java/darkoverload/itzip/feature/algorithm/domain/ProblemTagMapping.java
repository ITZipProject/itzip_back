package darkoverload.itzip.feature.algorithm.domain;

import darkoverload.itzip.feature.algorithm.entity.ProblemTagMappingEntity;
import darkoverload.itzip.feature.algorithm.entity.embedded.ProblemTagMappingId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemTagMapping {
    private ProblemTagMappingId problemTagMappingId;
    private Problem problemEntity;
    private ProblemTag problemTagEntity;

    public ProblemTagMappingEntity convertToEntity() {
        return ProblemTagMappingEntity.builder()
                .problemTagMappingId(problemTagMappingId)
                .problemEntity(this.problemEntity.convertToEntity())
                .problemTagEntity(this.problemTagEntity.convertToEntity())
                .build();
    }
}