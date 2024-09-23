package darkoverload.itzip.feature.algorithm.domain;

import darkoverload.itzip.feature.algorithm.entity.ProblemTagEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProblemTag {
    private Long bojTagId;
    private String displayName;
    private String displayNameSort;

    public ProblemTagEntity convertToEntity() {
        return ProblemTagEntity.builder()
                .bojTagId(this.bojTagId)
                .displayName(this.displayName)
                .displayNameSort(this.displayNameSort)
                .build();
    }
}