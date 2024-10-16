package darkoverload.itzip.feature.algorithm.entity.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * userProblemMapping 임베디드 키
 */
@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProblemMappingId implements Serializable {
    private Long userId;
    private Long problemId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProblemMappingId)) return false;
        UserProblemMappingId that = (UserProblemMappingId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(problemId, that.problemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, problemId);
    }
}
