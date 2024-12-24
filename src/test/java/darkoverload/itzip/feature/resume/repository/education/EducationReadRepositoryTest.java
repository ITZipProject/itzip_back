package darkoverload.itzip.feature.resume.repository.education;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.entity.EducationEntity;
import darkoverload.itzip.feature.resume.entity.ResumeBasicInfoEntity;
import darkoverload.itzip.feature.resume.entity.resume.ResumeEntity;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@SqlGroup({@Sql(value = "/sql/education/delete-education-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD), @Sql(value = "/sql/education/education-repository-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)})
@ContextConfiguration(classes = {TestQueryDslConfig.class})
@ActiveProfiles("test")
public class EducationReadRepositoryTest {

    @Autowired
    private EducationReadJpaRepository repository;

    private ResumeEntity resume = null;

    @BeforeEach
    void setUp() {
        ResumeBasicInfoEntity profileInfo = ResumeBasicInfoEntity.builder()
                .email("itzip@gmail.com")
                .introduction("잇집입니다.")
                .phone("010-2355-9839")
                .publicOnOff(PublicOnOff.YES)
                .subject("잇집 홍길동")
                .build();

        resume = ResumeEntity.builder().id(1L).basicInfo(profileInfo).imageUrl("https://itzip.com").links(List.of("잇집이력서")).userId(1L).fileUrls(new ArrayList<>()).build();
    }

    @Test
    void 교육_정보_리스트_조회() {
        List<EducationEntity> allByResumeId = repository.findAllByResumeId(1L);

        assertThat(allByResumeId).isEqualTo(List.of(EducationEntity.builder().id(1L).major("컴퓨터공학과").schoolName("잇집대학교").startDate(LocalDateTime.of(2024, 3, 26, 23, 59, 59)).endDate(LocalDateTime.of(2020, 3, 11, 23, 59, 59)).resume(resume).build(), EducationEntity.builder().id(2L).major("백엔드 과정").schoolName("잇집테킷").startDate(LocalDateTime.of(2024, 9, 28, 23, 59, 59)).endDate(LocalDateTime.of(2024, 3, 26, 23, 59, 59)).resume(resume).build()));

    }
}
