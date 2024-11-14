package darkoverload.itzip.feature.resume.domain.career;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.domain.resume.ResumeWorkTermType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class CareersTest {
    private Careers careers = null;

    @BeforeEach
    void setUp() {
        careers = Careers.of(List.of(Career.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).companyName("잇집회사").careerPosition("사원").department("개발팀").startDate(LocalDateTime.of(2022, 6, 10, 0, 0)).endDate(LocalDateTime.of(2023, 6, 10, 0, 0)).careerId(1L).build(), Career.builder().resume(new Resume("han@gmail.com", "010-2333-9800", "잇집 han입니다.", "잇집 han 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 2L, 2L, null)).companyName("잇집회사").careerPosition("팀장").department("개발팀").startDate(LocalDateTime.of(2015, 6, 10, 0, 0)).endDate(LocalDateTime.of(2023, 6, 10, 0, 0)).careerId(1L).build())).orElse(null);
    }

    @Test
    void 경력_일급_컬렉션_저장_테스트() {

        assertThat(careers).isEqualTo(new Careers(List.of(Career.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).companyName("잇집회사").careerPosition("사원").department("개발팀").startDate(LocalDateTime.of(2022, 6, 10, 0, 0)).endDate(LocalDateTime.of(2023, 6, 10, 0, 0)).careerId(1L).build(), Career.builder().resume(new Resume("han@gmail.com", "010-2333-9800", "잇집 han입니다.", "잇집 han 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 2L, 2L, null)).companyName("잇집회사").careerPosition("팀장").department("개발팀").startDate(LocalDateTime.of(2015, 6, 10, 0, 0)).endDate(LocalDateTime.of(2023, 6, 10, 0, 0)).careerId(1L).build())));
    }

    @Test
    void 일급_컬렉션_경력_삭제_테스트() {
        assertThat(careers.deleteCareers(List.of(Career.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).companyName("잇집회사").careerPosition("사원").department("개발팀").startDate(LocalDateTime.of(2022, 6, 10, 0, 0)).endDate(LocalDateTime.of(2023, 6, 10, 0, 0)).careerId(1L).build()))).isEqualTo(List.of(Career.builder().resume(new Resume("han@gmail.com", "010-2333-9800", "잇집 han입니다.", "잇집 han 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 2L, 2L, null)).companyName("잇집회사").careerPosition("팀장").department("개발팀").startDate(LocalDateTime.of(2015, 6, 10, 0, 0)).endDate(LocalDateTime.of(2023, 6, 10, 0, 0)).careerId(1L).build()));
    }

    @Test
    void 이력서_경력_근무_기간_시니어_총합_성공_테스트() {
        Map<Long, Resume> longResumeMap = Map.of(1L, new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null));

        Careers newCareer = Careers.of(List.of(Career.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).companyName("잇집회사").careerPosition("팀장").department("개발팀").startDate(LocalDateTime.of(2022, 6, 10, 0, 0)).endDate(LocalDateTime.of(2023, 6, 10, 0, 0)).careerId(1L).build(), Career.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).companyName("잇집외국계회사").careerPosition("사원").department("개발팀").startDate(LocalDateTime.of(2015, 6, 10, 0, 0)).endDate(LocalDateTime.of(2022, 6, 10, 0, 0)).careerId(2L).build())).orElse(null);

        assertThat(newCareer.searchResumeMakeWorkPeriod(longResumeMap)).isEqualTo(List.of(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", null, List.of("https://itzip.com"), null, 1L, 1L, ResumeWorkTermType.SENIOR.getName())));
    }

    @Test
    void 이력서_경력_근무_기간_주니어_총합_성공_테스트() {
        Map<Long, Resume> longResumeMap = Map.of(1L, new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null));

        Careers newCareer = Careers.of(List.of(Career.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).companyName("잇집회사").careerPosition("팀장").department("개발팀").startDate(LocalDateTime.of(2022, 6, 10, 0, 0)).endDate(LocalDateTime.of(2023, 6, 10, 0, 0)).careerId(1L).build())).orElse(null);

        assertThat(newCareer.searchResumeMakeWorkPeriod(longResumeMap)).isEqualTo(List.of(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", null, List.of("https://itzip.com"), null, 1L, 1L, ResumeWorkTermType.JUNIOR.getName())));
    }

    @Test
    void 이력서_경력_근무_기간_신입_성공_테스트() {
        Map<Long, Resume> longResumeMap = Map.of(1L, new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null));

        Careers newCareer = new Careers();
        assertThat(newCareer.searchResumeMakeWorkPeriod(longResumeMap)).isEqualTo(List.of(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", null, List.of("https://itzip.com"), null, 1L, 1L, ResumeWorkTermType.NEW_PERSON.getName())));
    }

}