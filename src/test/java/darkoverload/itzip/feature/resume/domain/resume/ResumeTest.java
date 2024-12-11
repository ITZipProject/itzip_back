package darkoverload.itzip.feature.resume.domain.resume;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.dto.resume.ResumeDto;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ResumeTest {

    @Test
    void 이력서_create_정적_메서드_성공_테스트() {
        ResumeDto resumeDto = new ResumeDto("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, null);

        Resume resume = Resume.create(resumeDto, 1L);


        assertThat(resume).isEqualTo(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, null, null, null));

    }

    @Test
    void 이력서_update_정적_메서드_성공_테스트() {
        ResumeDto resumeDto = new ResumeDto("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, null);

        Resume resume = Resume.update(resumeDto, 1L, 1L);

        assertThat(resume).isEqualTo(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null));
    }


    @Test
    void searchResume_테스트_성공_코드() {
        Resume resume = Resume.searchResume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", null, List.of("https://itzip.com"), null, 1L, 1L, null, null), "신입");
        assertThat(resume).isEqualTo(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", null, List.of("https://itzip.com"), null, 1L, 1L, "신입", null));
    }

    @Test
    void userIdEqualsCheck_테스트_성공_코드() {
        Resume resume = new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", null, List.of("https://itzip.com"), null, 1L, 1L, null, null);

        assertThatThrownBy(() -> resume.userIdEqualsCheck(2L)).isInstanceOf(RestApiException.class);
    }

}