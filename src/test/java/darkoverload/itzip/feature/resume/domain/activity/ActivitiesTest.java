package darkoverload.itzip.feature.resume.domain.activity;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.Resume;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ActivitiesTest {

    @Test
    void 활동_일급_컬렉션_저장_성공_테스트() {
        Activities activities = Activities.of(List.of(Activity.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).name("잇집 테스트 코드 활동").content("잇집 테스트 코드 완료 하여 수강증 부여").startDate(LocalDateTime.of(2023, 10, 20, 8, 30)).endDate(LocalDateTime.of(2024, 7, 30, 0, 0)).activityId(1L).build(), Activity.builder().resume(new Resume("han@gmail.com", "010-2333-9800", "잇집 han입니다.", "잇집 han 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 2L, 2L, null)).name("잇집 자바 활동").content("잇집 자바 수료증 부여").startDate(LocalDateTime.of(2024, 8, 15, 7, 30)).endDate(LocalDateTime.of(2024, 10, 30, 0, 0)).activityId(2L).build())).orElse(null);

        assertThat(activities).isEqualTo(new Activities(List.of(Activity.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).name("잇집 테스트 코드 활동").content("잇집 테스트 코드 완료 하여 수강증 부여").startDate(LocalDateTime.of(2023, 10, 20, 8, 30)).endDate(LocalDateTime.of(2024, 7, 30, 0, 0)).activityId(1L).build(), Activity.builder().resume(new Resume("han@gmail.com", "010-2333-9800", "잇집 han입니다.", "잇집 han 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 2L, 2L, null)).name("잇집 자바 활동").content("잇집 자바 수료증 부여").startDate(LocalDateTime.of(2024, 8, 15, 7, 30)).endDate(LocalDateTime.of(2024, 10, 30, 0, 0)).activityId(2L).build())));
    }

    @Test
    void 활동_일급_컬렉션_삭제_리스트_테스트() {
        Activities activities = Activities.of(List.of(Activity.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).name("잇집 테스트 코드 활동").content("잇집 테스트 코드 완료 하여 수강증 부여").startDate(LocalDateTime.of(2023, 10, 20, 8, 30)).endDate(LocalDateTime.of(2024, 7, 30, 0, 0)).activityId(1L).build(), Activity.builder().resume(new Resume("han@gmail.com", "010-2333-9800", "잇집 han입니다.", "잇집 han 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 2L, 2L, null)).name("잇집 자바 활동").content("잇집 자바 수료증 부여").startDate(LocalDateTime.of(2024, 8, 15, 7, 30)).endDate(LocalDateTime.of(2024, 10, 30, 0, 0)).activityId(2L).build())).orElse(null);

        assertThat(activities.deleteActivities(List.of(Activity.builder().resume(new Resume("itzip@gmail.com", "010-9955-0938", "잇집 park입니다.", "잇집 park 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 1L, 1L, null)).name("잇집 테스트 코드 활동").content("잇집 테스트 코드 완료 하여 수강증 부여").startDate(LocalDateTime.of(2023, 10, 20, 8, 30)).endDate(LocalDateTime.of(2024, 7, 30, 0, 0)).activityId(1L).build()))).isEqualTo(List.of(Activity.builder().resume(new Resume("han@gmail.com", "010-2333-9800", "잇집 han입니다.", "잇집 han 입니다. 저는 코딩이 좋아요.", PublicOnOff.YES, List.of("https://itzip.com"), null, 2L, 2L, null)).name("잇집 자바 활동").content("잇집 자바 수료증 부여").startDate(LocalDateTime.of(2024, 8, 15, 7, 30)).endDate(LocalDateTime.of(2024, 10, 30, 0, 0)).activityId(2L).build()));
    }

}
