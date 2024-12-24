package darkoverload.itzip.feature.resume.domain.resume.scrap;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.ResumeBasicInfo;
import darkoverload.itzip.feature.resume.entity.resume.ResumeEntity;
import darkoverload.itzip.feature.user.entity.Authority;
import darkoverload.itzip.feature.user.entity.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ResumeScrapTest {

    private long resumeId = 13L;
    private String userEmail = "test@gmail.com";

    @Test
    void 이력서_스크랩_카운터_레디스_키_테스트() {
        String redisKey = ResumeScrap.makeRedisKey(resumeId, userEmail);
        assertThat(redisKey).isEqualTo("RESUME_SCRAP:13:test@gmail.com");
    }

    @Test
    void 이력서_스크랩_정적_메서드_create_메서드_테스트() {
        UserEntity user = UserEntity.builder()
                .id(13L)
                .email("test@gmail.com")
                .nickname("test")
                .password("tttt1234@@")
                .imageUrl("https://itzip.co.kr")
                .authority(Authority.USER)
                .build();

        ResumeBasicInfo resumeBasicInfo = ResumeBasicInfo.builder()
                .email("itzip@gmail.com")
                .phone("010-0009-3845")
                .subject("잇집입니다.")
                .publicOnOff(PublicOnOff.YES)
                .introduction("잇집입니다. 저는 코딩이 좋아요.")
                .build();

        ResumeEntity resume = ResumeEntity.builder()
                .id(13L)
                .basicInfo(resumeBasicInfo.toEntity())
                .links(List.of("han@itzip.com"))
                .imageUrl("https://itzip.co.kr")
                .userId(13L)
                .fileUrls(List.of("https://itzip.co.kr/resume.pdf"))
                .scrapCount(0)
                .build();


        assertThat(ResumeScrap.createScrap(user, resume)).isEqualTo(
                ResumeScrap.builder()
                        .user(user)
                        .resume(resume)
                        .build()
        );
    }

}