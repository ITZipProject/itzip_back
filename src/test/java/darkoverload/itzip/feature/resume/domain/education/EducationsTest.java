package darkoverload.itzip.feature.resume.domain.education;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.education.EducationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class EducationsTest {

    private Optional<Educations> educations;

    @BeforeEach
    void setUp() {
        educations = Educations.of(List.of(Education.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null)).schoolName("잇집대학교").major("소프트웨어 잇집과").startDate(LocalDateTime.of(2009, 3, 7, 0, 0)).endDate(LocalDateTime.of(2013, 3, 7, 0, 0)).educationId(1L).build(), Education.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null)).schoolName("잇집대학원").major("인공지능").startDate(LocalDateTime.of(2013, 4, 7, 0, 0)).endDate(LocalDateTime.of(2016, 3, 7, 0, 0)).educationId(2L).build()));
    }

    @Test
    void 일급_컬렉션_저장_of_성공_테스트() {
        List<EducationDto> educationDtoList = List.of(new EducationDto("잇집대학교", "소프트웨어 잇집과", LocalDateTime.of(2009, 3, 7, 0, 0), LocalDateTime.of(2013, 3, 7, 0, 0), 1L), new EducationDto("잇집대학원", "인공지능", LocalDateTime.of(2013, 4, 7, 0, 0), LocalDateTime.of(2016, 3, 7, 0, 0), 2L));


        assertThat(educations).isEqualTo(Educations.of(educationDtoList, new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null)));
    }

    @Test
    void 일급_컬렉션_삭제_리스트_성공_테스트() {
        assertThat(educations.get().deleteEducations(List.of(new Education(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null), "잇집대학교", "소프트웨어 잇집과", LocalDateTime.of(2009, 3, 7, 0, 0), LocalDateTime.of(2013, 3, 7, 0, 0), 1L)))).isEqualTo(List.of(new Education(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null), "잇집대학원", "인공지능", LocalDateTime.of(2013, 4, 7, 0, 0), LocalDateTime.of(2016, 3, 7, 0, 0), 2L)));
    }

}