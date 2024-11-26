package darkoverload.itzip.feature.resume.repository.myskill;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.entity.MySkillEntity;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@SqlGroup({@Sql(value = "/sql/myskill/delete-myskill-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD), @Sql(value = "/sql/myskill/myskill-repository-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)})
@ContextConfiguration(classes = {TestQueryDslConfig.class})
@ActiveProfiles("test")
public class MySkillReadRepositoryTest {

    @Autowired
    private MySkillReadJpaRepository repository;

    private ResumeEntity resume = null;

    @BeforeEach
    void setUp() {
        resume = ResumeEntity.builder().id(1L).email("itzip@gmail.com").imageUrl("https://itzip.com").introduction("잇집입니다.").links(List.of("잇집이력서")).phone("010-2355-9839").publicOnOff(PublicOnOff.YES).subject("잇집 홍길동").userId(1L).build();
    }

    @Test
    void 기술_정보_리스트_조회() {
        List<MySkillEntity> allByResumeId = repository.findAllByResumeId(1L);

        assertThat(allByResumeId).isEqualTo(List.of(MySkillEntity.builder().id(1L).name("Java").resume(resume).build(), MySkillEntity.builder().id(2L).name("PostgreSQL").resume(resume).build()));

    }

}
