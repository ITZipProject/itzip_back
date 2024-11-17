package darkoverload.itzip.feature.resume.domain.education;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.education.EducationDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class EducationTest {

    @Test
    void 교육_정적_메소드_업데이트_테스트() {

        Education education = Education.update(new EducationDto("잇집대학교", "소프트웨어 잇집과", LocalDateTime.of(2009, 3, 7, 0, 0), LocalDateTime.of(2013, 3, 7, 0, 0), 1L), Resume.builder().email("itzip@gmail.com").phone("010-9955-0938").subject("잇집 park입니다.").publicOnOff(PublicOnOff.YES).links(List.of("https://itzip.com")).introduction("잇집 park 입니다. 저는 코딩이 좋아요.").userId(1L).resumeId(1L).build());

        assertThat(education).isEqualTo(new Education(Resume.builder().email("itzip@gmail.com").phone("010-9955-0938").subject("잇집 park입니다.").publicOnOff(PublicOnOff.YES).links(List.of("https://itzip.com")).introduction("잇집 park 입니다. 저는 코딩이 좋아요.").userId(1L).resumeId(1L).build(), "잇집대학교", "소프트웨어 잇집과", LocalDateTime.of(2009, 3, 7, 0, 0), LocalDateTime.of(2013, 3, 7, 0, 0), 1L));
    }

}