package darkoverload.itzip.feature.resume.repository.activity;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.entity.ActivityEntity;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import darkoverload.itzip.global.config.querydsl.TestQueryDslConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@SqlGroup({@Sql(value = "/sql/activity/delete-activity-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD), @Sql(value = "/sql/activity/activity-repository-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)})
@ContextConfiguration(classes = {TestQueryDslConfig.class})
@ActiveProfiles("test")
public class ActivityReadRepositoryTest {

    @Autowired
    private ActivityReadJpaRepository repository;

    private ResumeEntity resume = null;

    @BeforeEach
    void setUp() {
        resume = ResumeEntity.builder().id(1L).email("itzip@gmail.com").imageUrl("https://itzip.com").introduction("잇집입니다.").links(List.of("잇집이력서")).phone("010-2355-9839").publicOnOff(PublicOnOff.YES).subject("잇집 홍길동").userId(1L).build();
    }

    @Test
    void 활동_이력_리스트_조회() {
        List<ActivityEntity> allByResumeId = repository.findAllByResumeId(1L);

        assertThat(allByResumeId).isEqualTo(List.of(ActivityEntity.builder().id(1L).content("무박 3일동안 해커톤 통해서 우수상 수상하였습니다.").name("해커톤상").startDate(LocalDateTime.of(2024, 8, 20, 10, 0, 5)).endDate(LocalDateTime.of(2024, 8, 23, 10, 0, 5)).resume(resume).build(), ActivityEntity.builder().id(2L).content("동아리 활동").name("코딩 동아리").startDate(LocalDateTime.of(2022, 11, 20, 10, 0, 5)).endDate(LocalDateTime.of(2023, 11, 20, 10, 0, 5)).resume(resume).build()));
    }

}
