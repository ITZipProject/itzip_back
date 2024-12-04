package darkoverload.itzip.feature.job.domain.job;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class JobInfoIdsTest {

    @Test
    void 정적_메소드_deleteIds_테스트() {
        JobInfoIds result = JobInfoIds.deleteIds(Set.of(1L, 2L, 3L, 4L, 5L), Set.of(3L, 4L, 5L, 6L));
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void 리스트_자르기_테스트() {
        JobInfoIds jobInfoIds = new JobInfoIds(List.of(1L, 2L, 3L, 4L, 5L));

        assertThat(jobInfoIds.subList(0, 3)).isEqualTo((List.of(1L, 2L, 3L)));
    }

}