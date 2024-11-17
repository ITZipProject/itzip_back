package darkoverload.itzip.feature.resume.domain.career;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.domain.resume.ResumeWorkTermType;
import darkoverload.itzip.feature.resume.dto.career.CareerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CareersTest {
    private Optional<Careers> careers;

    @BeforeEach
    void setUp() {
        careers = Careers.of(List.of(Career.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).companyName("잇집회사").careerPosition("사원").department("개발팀").startDate(LocalDateTime.of(2022, 6, 10, 0, 0)).endDate(LocalDateTime.of(2023, 6, 10, 0, 0)).careerId(1L).build(), Career.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).companyName("잇집회사").careerPosition("팀장").department("개발팀").startDate(LocalDateTime.of(2015, 6, 10, 0, 0)).endDate(LocalDateTime.of(2023, 6, 10, 0, 0)).careerId(2L).build()));
    }

    @Test
    void 경력_일급_컬렉션_of_저장_테스트() {
        assertThat(careers).isEqualTo(Careers.of(List.of(new CareerDto("잇집회사", "사원", "개발팀", LocalDateTime.of(2022, 6, 10, 0, 0), LocalDateTime.of(2023, 6, 10, 0, 0), 1L), new CareerDto("잇집회사", "팀장", "개발팀", LocalDateTime.of(2015, 6, 10, 0, 0), LocalDateTime.of(2023, 6, 10, 0, 0), 2L)), new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)));
    }

    @Test
    void 경력_일급_컬렉션_저장_테스트() {
        assertThat(careers.get()).isEqualTo(new Careers(List.of(Career.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).companyName("잇집회사").careerPosition("사원").department("개발팀").startDate(LocalDateTime.of(2022, 6, 10, 0, 0)).endDate(LocalDateTime.of(2023, 6, 10, 0, 0)).careerId(1L).build(), Career.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).companyName("잇집회사").careerPosition("팀장").department("개발팀").startDate(LocalDateTime.of(2015, 6, 10, 0, 0)).endDate(LocalDateTime.of(2023, 6, 10, 0, 0)).careerId(2L).build())));
    }

    @Test
    void 일급_컬렉션_경력_삭제_테스트() {
        assertThat(careers.get().deleteCareers(List.of(Career.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).companyName("잇집회사").careerPosition("사원").department("개발팀").startDate(LocalDateTime.of(2022, 6, 10, 0, 0)).endDate(LocalDateTime.of(2023, 6, 10, 0, 0)).careerId(1L).build()))).isEqualTo(List.of(Career.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).companyName("잇집회사").careerPosition("팀장").department("개발팀").startDate(LocalDateTime.of(2015, 6, 10, 0, 0)).endDate(LocalDateTime.of(2023, 6, 10, 0, 0)).careerId(2L).build()));
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