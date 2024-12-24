package darkoverload.itzip.feature.resume.domain.career;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.ResumeBasicInfo;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.career.CareerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CareerTest {

    private ResumeBasicInfo resumeBasicInfo;

    @BeforeEach
    void setUp() {
        resumeBasicInfo = ResumeBasicInfo.builder()
                .email("itzip@gmail.com")
                .phone("010-9955-0938")
                .publicOnOff(PublicOnOff.YES)
                .introduction("잇집 park 입니다. 저는 코딩이 좋아요.")
                .subject("잇집 park입니다.")
                .build();
    }

    @Test
    void 경력_업데이트_정적_메소드_성공_테스트() {

        Resume resume = Resume.builder().resumeBasicInfo(resumeBasicInfo).links(List.of("https://itzip.com")).userId(1L).resumeId(1L).build();

        Career career = Career.update(new CareerDto("잇집회사", "사원", "개발팀", LocalDateTime.of(2022,6,10,0,0), LocalDateTime.of(2023,6,10,0,0), 1L), resume);

        assertThat(career).isEqualTo(new Career(Resume.builder().resumeBasicInfo(resumeBasicInfo).links(List.of("https://itzip.com")).userId(1L).resumeId(1L).build(), "잇집회사", "사원", "개발팀", LocalDateTime.of(2022,6,10,0,0), LocalDateTime.of(2023,6,10,0,0), 1L));
    }

    @Test
    void 아이디_이력서_동일_true_테스트() {
        Career career = Career.update(new CareerDto("잇집회사", "사원", "개발팀", LocalDateTime.of(2022,6,10,0,0), LocalDateTime.of(2023,6,10,0,0), 1L), Resume.builder().resumeBasicInfo(resumeBasicInfo).links(List.of("https://itzip.com")).userId(1L).resumeId(1L).build());

        assertThat(career.isResumeIdEquals(1L)).isTrue();
    }

    @Test
    void 아이디_이력서_동일_false_테스트() {
        Career career = Career.update(new CareerDto("잇집회사", "사원", "개발팀", LocalDateTime.of(2022,6,10,0,0), LocalDateTime.of(2023,6,10,0,0), 1L), Resume.builder().resumeBasicInfo(resumeBasicInfo).links(List.of("https://itzip.com")).userId(1L).resumeId(1L).build());

        assertThat(career.isResumeIdEquals(2L)).isFalse();
    }

    @Test
    void 이력서_근무_기간_조회_테스트() {
        Career career = Career.update(new CareerDto("잇집회사", "사원", "개발팀", LocalDateTime.of(2022,6,10,0,0), LocalDateTime.of(2023,6,10,0,0), 1L), Resume.builder().resumeBasicInfo(resumeBasicInfo).links(List.of("https://itzip.com")).userId(1L).resumeId(1L).build());

        assertThat(career.workTerm()).isEqualTo(12L);
    }

}