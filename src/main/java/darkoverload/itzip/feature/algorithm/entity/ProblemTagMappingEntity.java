package darkoverload.itzip.feature.algorithm.entity;

import darkoverload.itzip.feature.algorithm.domain.ProblemTagMapping;
import darkoverload.itzip.feature.algorithm.entity.embedded.ProblemTagMappingId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//문제랑 테그들을 매핑하는 테이블
@Entity
@Table(name = "problem_tag_mapping")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProblemTagMappingEntity {

    @EmbeddedId
    private ProblemTagMappingId problemTagMappingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("problemId")
    @JoinColumn(name = "problem_id", nullable = false)
    private ProblemEntity problemEntity; // 문제 ID (외래 키)

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("problemTagId")
    @JoinColumn(name = "boj_tag_id", nullable = false)
    private ProblemTagEntity problemTagEntity; // 태그 ID (외래 키)

    public ProblemTagMapping convertToDomain() {
        return ProblemTagMapping.builder()
                .problemTagMappingId(problemTagMappingId)
                .problemEntity(this.problemEntity.convertToDomain())
                .problemTagEntity(this.problemTagEntity.convertToDomain())
                .build();
    }
}