package darkoverload.itzip.feature.resume.repository.career;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.entity.CareerEntity;
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

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@SqlGroup({@Sql(value = "/sql/career/delete-career-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD), @Sql(value = "/sql/career/career-repository-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)})
@ContextConfiguration(classes = {TestQueryDslConfig.class})
@ActiveProfiles("test")
public class CareerReadRepositoryTest {

    @Autowired
    private CareerReadJpaRepository repository;

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

        resume = ResumeEntity.builder().id(1L).imageUrl("https://itzip.com").links(List.of("잇집이력서")).basicInfo(profileInfo).userId(1L).fileUrls(new ArrayList<>()).build();
    }

    @Test
    void 경력_정보_리스트_조회() {
        List<CareerEntity> allByResumeId = repository.findAllByResumeId(1L);

        assertThat(allByResumeId).isEqualTo(List.of(CareerEntity.builder().id(1L).startDate(LocalDateTime.of(2022, 9, 26, 23, 59, 59)).endDate(LocalDateTime.of(2024, 9, 26, 23, 59, 59)).careerPosition("사원").companyName("잇집회사").department("IT팀").resume(resume).build(), CareerEntity.builder().id(2L).startDate(LocalDateTime.of(2021, 5, 26, 23, 59, 59)).endDate(LocalDateTime.of(2022, 5, 26, 23, 59, 59)).careerPosition("사원").companyName("텔회사").department("IT팀").resume(resume).build()));
    }

}
