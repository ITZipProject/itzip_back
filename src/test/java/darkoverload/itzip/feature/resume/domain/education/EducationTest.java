package darkoverload.itzip.feature.resume.domain.education;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.ResumeBasicInfo;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.education.EducationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class EducationTest {
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
    void 교육_정적_메소드_업데이트_테스트() {

        Education education = Education.update(new EducationDto("잇집대학교", "소프트웨어 잇집과", LocalDateTime.of(2009, 3, 7, 0, 0), LocalDateTime.of(2013, 3, 7, 0, 0), 1L), Resume.builder().links(List.of("https://itzip.com")).resumeBasicInfo(resumeBasicInfo).userId(1L).resumeId(1L).build());

        assertThat(education).isEqualTo(new Education(Resume.builder().links(List.of("https://itzip.com")).resumeBasicInfo(resumeBasicInfo).userId(1L).resumeId(1L).build(), "잇집대학교", "소프트웨어 잇집과", LocalDateTime.of(2009, 3, 7, 0, 0), LocalDateTime.of(2013, 3, 7, 0, 0), 1L));
    }

}