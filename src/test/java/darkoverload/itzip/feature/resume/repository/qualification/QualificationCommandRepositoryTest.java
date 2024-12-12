package darkoverload.itzip.feature.resume.repository.qualification;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.entity.ProfileInfoEntity;
import darkoverload.itzip.feature.resume.entity.QualificationEntity;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@SqlGroup({@Sql(value = "/sql/qualification/delete-qualification-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD), @Sql(value = "/sql/qualification/qualification-repository-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)})
@ContextConfiguration(classes = {TestQueryDslConfig.class})
@ActiveProfiles("test")
public class QualificationCommandRepositoryTest {

    @Autowired
    private QualificationReadJpaRepository repository;
    private ResumeEntity resume = null;

    @BeforeEach
    void setUp() {
        ProfileInfoEntity profileInfo = ProfileInfoEntity.builder()
                .email("itzip@gmail.com")
                .introduction("잇집입니다.")
                .phone("010-2355-9839")
                .publicOnOff(PublicOnOff.YES)
                .subject("잇집 홍길동")
                .build();

        resume = ResumeEntity.builder().id(1L).profileInfo(profileInfo).imageUrl("https://itzip.com").links(List.of("잇집이력서")).userId(1L).fileUrls(new ArrayList<>()).build();
    }

    @Test
    void 자격증_리스트_조회() {
        List<QualificationEntity> allByResumeId = repository.findAllByResumeId(1L);

        assertThat(allByResumeId).isEqualTo(List.of(QualificationEntity.builder().id(1L).name("정보처리기사").organization("잇집기관").qualificationDate(LocalDateTime.of(2024, 8, 20, 23, 59, 59)).resume(resume).build(), QualificationEntity.builder().id(2L).name("자바처리 자격증").organization("잇집기관").qualificationDate(LocalDateTime.of(2024, 8, 9, 23, 59, 59)).resume(resume).build()));
    }
}
