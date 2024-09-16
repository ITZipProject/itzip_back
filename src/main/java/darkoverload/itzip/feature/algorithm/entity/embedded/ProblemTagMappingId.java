package darkoverload.itzip.feature.algorithm.entity.embedded;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * problemTagMapping 임베디드 키
 */
@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemTagMappingId implements Serializable {
    private Long problemId;
    private Long problemTagId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProblemTagMappingId)) return false;
        ProblemTagMappingId that = (ProblemTagMappingId) o;
        return Objects.equals(problemId, that.problemId) && Objects.equals(problemTagId, that.problemTagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(problemId, problemTagId);
    }
}
