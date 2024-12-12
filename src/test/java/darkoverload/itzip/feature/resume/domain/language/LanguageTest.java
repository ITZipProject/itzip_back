package darkoverload.itzip.feature.resume.domain.language;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.ProfileInfo;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.language.LanguageDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

class LanguageTest {
    private ProfileInfo profileInfo;

    @BeforeEach
    void setUp() {
        profileInfo = ProfileInfo.builder()
                .email("itzip@gmail.com")
                .phone("010-9955-0938")
                .subject("잇집 park입니다.")
                .publicOnOff(PublicOnOff.YES)
                .introduction("잇집 park 입니다. 저는 코딩이 좋아요.")
                .build();
    }

    @Test
    void 언어_정적_메소드_저장_성공_테스트() {
        Language language = Language.update(new LanguageDto("잇집토익상", "780", LocalDateTime.of(2024, 10, 25, 0, 0), 1L), new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null));

        Assertions.assertThat(language).isEqualTo(new Language(Resume.builder().links(List.of("https://itzip.com")).profileInfo(profileInfo).userId(1L).resumeId(1L).build(), "잇집토익상", "780", LocalDateTime.of(2024, 10, 25, 0, 0), 1L));
    }

}