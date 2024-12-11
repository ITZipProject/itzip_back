package darkoverload.itzip.feature.resume.domain.myskill;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import darkoverload.itzip.feature.resume.dto.myskill.MySkillsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MySkillsTest {

    private Optional<MySkills> mySkills;

    @BeforeEach
    void setUp() {
        mySkills = MySkills.of(List.of(MySkill.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null)).name("python").mySkillId(1L).build(), MySkill.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null)).name("java").mySkillId(2L).build()));
    }

    @Test
    void 일급_컬렉션_스킬_리스트_저장_성공() {
        assertThat(mySkills).isEqualTo(MySkills.of(List.of(new MySkillsDto("python", 1L), new MySkillsDto("java", 2L)), new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null)));
    }

    @Test
    void 일급_컬렉션_스킬_삭제_리스트_성공() {
        assertThat(mySkills.get().deleteMySkills(List.of(MySkill.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null)).name("python").mySkillId(1L).build()))).isEqualTo(List.of(MySkill.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null, null)).name("java").mySkillId(2L).build()));
    }

}