package darkoverload.itzip.feature.techinfo.dto.scrap;

import darkoverload.itzip.feature.techinfo.domain.Scrap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Scrap 상태를 나타내는 DTO (Data Transfer Object) 클래스.
 * 특정 유저가 특정 포스트에 대해 스크랩을 눌렀는지 여부를 관리함.
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScrapStatusDto {

    /**
     * 유저 ID (스크렙을 누른 유저).
     */
    private Long userId;

    /**
     * 포스트 ID (스크랩이 눌린 포스트).
     */
    private String postId;

    /**
     * 스크랩 상태 (true면 스크랩 추가, false면 스크랩 취소).
     */
    private Boolean isScrapped;

    public Scrap convertToDomain() {
        return Scrap.builder()
                .userId(this.userId)
                .postId(this.postId)
                .build();
    }
}