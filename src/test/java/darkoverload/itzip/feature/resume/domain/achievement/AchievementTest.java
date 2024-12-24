package darkoverload.itzip.feature.resume.domain.achievement;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.ResumeBasicInfo;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.achievement.AchievementDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AchievementTest {

    private ResumeBasicInfo resumeBasicInfo;

    @BeforeEach
    void setUp() {
        resumeBasicInfo = ResumeBasicInfo.builder()
                .email("itzip@gmail.com")
                .phone("010-9955-0938")
                .subject("잇집 park입니다.")
                .publicOnOff(PublicOnOff.YES)
                .introduction("잇집 park 입니다. 저는 코딩이 좋아요.")
                .build();
    }

    @Test
    void 수상_업데이트_정적메소드_테스트() {
        AchievementDto achievementDto = new AchievementDto("잇집상", "잇집기관", LocalDateTime.of(2024, 10, 20, 10, 30), "잇집상을 수여합니다.", 1L);

        Resume resume = Resume.builder().resumeBasicInfo(resumeBasicInfo).links(List.of("https://itzip.com")).userId(1L).resumeId(1L).build();

        Achievement achievement = Achievement.update(achievementDto, resume);


        assertThat(achievement).isEqualTo(Achievement.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null, 0)).achievementId(1L).name("잇집상").organization("잇집기관").content("잇집상을 수여합니다.").achievementId(1L).build());
    }

}