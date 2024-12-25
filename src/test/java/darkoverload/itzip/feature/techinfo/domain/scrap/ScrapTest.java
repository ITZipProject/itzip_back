package darkoverload.itzip.feature.techinfo.domain.scrap;

import static org.assertj.core.api.Assertions.assertThat;

import darkoverload.itzip.feature.techinfo.dto.scrap.ScrapStatus;
import org.junit.jupiter.api.Test;

class ScrapTest {

    @Test
    void 스크랩_생성_시_모든_필드가_올바르게_매핑된다() {
        // given
        String id = "675979e6605cda1eaf5d4c20";
        String postId = "675979e6605cda1eaf5d4c17";
        Long userId = 100L;

        // when
        Scrap result = Scrap.builder()
                .id(id)
                .postId(postId)
                .userId(userId)
                .build();

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getPostId()).isEqualTo(postId);
        assertThat(result.getUserId()).isEqualTo(userId);
    }

    @Test
    void 스크랩_상태로부터_스크랩을_생성한다() {
        // given
        ScrapStatus scrapStatus = ScrapStatus.builder()
                .postId("675979e6605cda1eaf5d4c17")
                .userId(100L)
                .isScrapped(true)
                .build();

        // when
        Scrap result = Scrap.from(scrapStatus);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getPostId()).isEqualTo(scrapStatus.getPostId());
        assertThat(result.getUserId()).isEqualTo(scrapStatus.getUserId());
    }

}