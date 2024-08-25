package darkoverload.itzip.feature.algorithm.domain;


import darkoverload.itzip.feature.algorithm.entity.ProblemEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Problem {
    private Long problemId;
    private String title;
    private Integer level;
    private Integer acceptedUserCount;
    private Integer averageTries;

    public ProblemEntity convertToEntity() {
        return ProblemEntity.builder()
                .problemId(this.problemId)
                .title(this.title)
                .level(this.level)
                .acceptedUserCount(this.acceptedUserCount)
                .averageTries(this.averageTries)
                .build();
    }
}