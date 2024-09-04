package darkoverload.itzip.feature.algorithm.domain;

import darkoverload.itzip.feature.algorithm.entity.ProblemTagMappingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemTagMapping {
    private Long id;
    private Problem problemEntity;
    private ProblemTag problemTagEntity;

    public ProblemTagMappingEntity convertToEntity() {
        return ProblemTagMappingEntity.builder()
                .id(this.id)
                .problemEntity(this.problemEntity.convertToEntity())
                .problemTagEntity(this.problemTagEntity.convertToEntity())
                .build();
    }
}