package darkoverload.itzip.feature.algorithm.domain;

import darkoverload.itzip.feature.algorithm.entity.SolvedacUserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolvedacUser {
    private Long userId;
    private String username;
    private Integer rating;
    private Integer rank;
    private LocalDateTime updateTime;
    private String profileImageUrl;
    private Integer solvedClass;
    private Integer tier;

    public SolvedacUserEntity convertToEntity() {
        return SolvedacUserEntity.builder()
                .userId(this.userId)
                .username(this.username)
                .rating(this.rating)
                .rank(this.rank)
                .updateTime(this.updateTime)
                .profileImageUrl(this.profileImageUrl)
                .solvedClass(this.solvedClass)
                .tier(this.tier)
                .build();
    }
}