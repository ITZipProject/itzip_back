package darkoverload.itzip.feature.resume.domain.achievement;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AchievementsTest {

    @Test
    void 수상_일급_컬렉션_저장_성공_테스트() {
        Achievements achievements = Achievements.of(List.of(Achievement.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).achievementId(1L).name("잇집상").organization("잇집기관").content("잇집상을 수여합니다.").achievementId(1L).build(), Achievement.builder().resume(new Resume("han@gmail.com", "010-2333-9800", "잇집 han입니다.", "잇집 han 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 2L, 2L, null)).achievementId(2L).name("잇집상").organization("잇집기관").content("han에게 잇집상을 수여합니다.").achievementId(1L).build())).orElse(null);


        assertThat(achievements).isEqualTo(new Achievements(List.of(Achievement.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).achievementId(1L).name("잇집상").organization("잇집기관").content("잇집상을 수여합니다.").achievementId(1L).build(), Achievement.builder().resume(new Resume("han@gmail.com", "010-2333-9800", "잇집 han입니다.", "잇집 han 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 2L, 2L, null)).achievementId(2L).name("잇집상").organization("잇집기관").content("han에게 잇집상을 수여합니다.").achievementId(1L).build())));
    }


    @Test
    void 수상_일급_컬렉션_삭제_리스트_테스트() {
        Achievements achievements = Achievements.of(List.of(Achievement.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).achievementId(1L).name("잇집상").organization("잇집기관").content("잇집상을 수여합니다.").achievementId(1L).build(), Achievement.builder().resume(new Resume("han@gmail.com", "010-2333-9800", "잇집 han입니다.", "잇집 han 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 2L, null)).achievementId(2L).name("잇집상").organization("잇집기관").content("han에게 잇집상을 수여합니다.").achievementId(1L).build())).orElse(null);


        assertThat(achievements.deleteAchievements(List.of(Achievement.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).achievementId(1L).name("잇집상").organization("잇집기관").content("잇집상을 수여합니다.").achievementId(1L).build()))).isEqualTo(List.of(Achievement.builder().resume(new Resume("han@gmail.com", "010-2333-9800", "잇집 han입니다.", "잇집 han 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 2L, null)).achievementId(2L).name("잇집상").organization("잇집기관").content("han에게 잇집상을 수여합니다.").achievementId(1L).build()));
    }

}