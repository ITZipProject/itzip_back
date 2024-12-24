package darkoverload.itzip.feature.resume.domain.qualification;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.qualification.QualificationDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QualificationTest {

    @Test
    void 자격증_업데이트_정적_메소드_성공_테스트() {
        Qualification qualification = Qualification.update(new QualificationDto("잇집기관", LocalDateTime.of(2022,6,11,0,0), "잇집 자격증", 1L), new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null, 0));

        assertThat(qualification).isEqualTo(new Qualification(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null, 0), "잇집기관", LocalDateTime.of(2022,6,11,0,0), "잇집 자격증", 1L));
    }

}