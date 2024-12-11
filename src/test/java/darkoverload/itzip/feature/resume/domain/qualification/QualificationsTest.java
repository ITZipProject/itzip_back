package darkoverload.itzip.feature.resume.domain.qualification;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.qualification.QualificationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class QualificationsTest {

    private Optional<Qualifications> qualifications;

    @BeforeEach
    void setUp() {
        qualifications = Qualifications.of(List.of(Qualification.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null)).qualificationId(1L).organization("잇집기관").name("잇집 자격증").qualificationDate(LocalDateTime.of(2022,6,11,0,0)).build(), Qualification.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null)).qualificationId(2L).organization("잇집기관").name("React Master 자격증").qualificationDate(LocalDateTime.of(2023,7,11,0,0)).build()));
    }

    @Test
    void 자격증_of_저장_성공_테스트() {
        assertThat(qualifications).isEqualTo(Qualifications.of(List.of(new QualificationDto("잇집기관", LocalDateTime.of(2022,6,11,0,0), "잇집 자격증", 1L), new QualificationDto("잇집기관", LocalDateTime.of(2023,7,11,0,0), "React Master 자격증", 2L)), new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null)));
    }

    @Test
    void 자격증_삭제_리스트_성공_테스트() {
        List<Qualification> deleteList = List.of(new Qualification(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null), "잇집기관", LocalDateTime.of(2022,6,11,0,0), "잇집 자격증", 1L));
        assertThat(qualifications.get().deleteQualifications(deleteList)).isEqualTo(List.of(new Qualification(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null), "잇집기관", LocalDateTime.of(2023,7,11,0,0), "React Master 자격증", 2L)));
    }

}