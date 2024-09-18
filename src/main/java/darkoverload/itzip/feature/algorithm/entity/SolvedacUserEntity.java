package darkoverload.itzip.feature.algorithm.entity;

import darkoverload.itzip.feature.algorithm.domain.SolvedacUser;
import darkoverload.itzip.feature.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//솔브드 ac 사용자 정보를 저장할 엔티티
@Entity
@Table(name = "solvedac_users")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SolvedacUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // 유저 ID

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")  // 외부 키로 매핑 (UserEntity와의 관계 설정)
    private UserEntity userEntity;

    @Column(nullable = false, name = "username")
    private String username; // 유저 이름

    @Column(name = "rating")
    private Integer rating; // 솔브드 ac 레이팅

    @Column(name = "rank")
    private Integer rank; // 솔브드 ac 랭킹

    @Column(name = "update_time")
    private LocalDateTime updateTime; // 문제 업데이트 시간

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "class")
    private Integer solvedClass;

    @Column(name = "tier")
    private Integer tier;

    public SolvedacUser convertToDomain() {
        return SolvedacUser.builder()
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