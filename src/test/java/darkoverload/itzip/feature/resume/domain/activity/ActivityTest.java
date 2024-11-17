package darkoverload.itzip.feature.resume.domain.activity;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.activity.ActivityDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityTest {

    @Test
    void 활동_업데이트_정적_메소드_성공_테스트() {
        Activity activity = Activity.update(new ActivityDto("잇집 테스트 코드 활동", "잇집 테스트 코드 완료 하여 수강증 부여", LocalDateTime.of(2023, 10, 20, 8, 30), LocalDateTime.of(2024, 7, 30, 0, 0), 1L), Resume.builder().email("itzip@gmail.com").phone("010-9955-0938").subject("잇집 park입니다.").publicOnOff(PublicOnOff.YES).links(List.of("https://itzip.com")).introduction("잇집 park 입니다. 저는 코딩이 좋아요.").userId(1L).resumeId(1L).build());

        assertThat(activity).isEqualTo(new Activity(Resume.builder().email("itzip@gmail.com").phone("010-9955-0938").subject("잇집 park입니다.").publicOnOff(PublicOnOff.YES).links(List.of("https://itzip.com")).introduction("잇집 park 입니다. 저는 코딩이 좋아요.").userId(1L).resumeId(1L).build(), "잇집 테스트 코드 활동", "잇집 테스트 코드 완료 하여 수강증 부여", LocalDateTime.of(2023, 10, 20, 8, 30), LocalDateTime.of(2024, 7, 30, 0, 0), 1L));
    }

}