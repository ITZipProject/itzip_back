package darkoverload.itzip.feature.resume.repository.achievement;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.entity.AchievementEntity;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import darkoverload.itzip.feature.resume.service.resume.port.achievement.AchievementReadRepository;
import darkoverload.itzip.global.config.querydsl.TestQueryDslConfig;
import org.assertj.core.api.Assertions;
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

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@SqlGroup({@Sql(value = "/sql/achievement/delete-achievement-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD), @Sql(value = "/sql/achievement/achievement-repository-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)})
@ContextConfiguration(classes = {TestQueryDslConfig.class})
@ActiveProfiles("test")
public class AchievementReadRepositoryTest {

    @Autowired
    private AchievementReadJpaRepository repository;

    private ResumeEntity resume = null;

    @BeforeEach
    void setUp() {
        resume = ResumeEntity.builder().id(1L).email("itzip@gmail.com").imageUrl("https://itzip.com").introduction("잇집입니다.").links(List.of("잇집이력서")).phone("010-2355-9839").publicOnOff(PublicOnOff.YES).subject("잇집 홍길동").userId(1L).build();
    }

    @Test
    void 수상_이력_정보_리스트_조회() {
        List<AchievementEntity> allByResumeId = repository.findAllByResumeId(1L);

        assertThat(allByResumeId).isEqualTo(List.of(AchievementEntity.builder().id(1L).achievementDate(LocalDateTime.of(2024, 8, 20, 10, 10, 10)).content("자바 잘해서줍니다.").name("잇집자바상").organization("잇집").resume(resume).build(), AchievementEntity.builder().id(2L).achievementDate(LocalDateTime.of(2024, 6, 20, 9, 10, 10)).content("코틀린 잘해서줌").name("잇집코틀린상").organization("잇집").resume(resume).build()));
    }

}
