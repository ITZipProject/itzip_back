package darkoverload.itzip.feature.resume.repository.language;

import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.entity.LanguageEntity;
import darkoverload.itzip.feature.resume.entity.ProfileInfoEntity;
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
@SqlGroup({@Sql(value = "/sql/language/delete-language-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD), @Sql(value = "/sql/language/language-repository-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)})
@ContextConfiguration(classes = {TestQueryDslConfig.class})
@ActiveProfiles("test")
public class LanguageReadReadRepositoryTest {

    @Autowired
    private LanguageReadJpaRepository repository;

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
    void 언어_취득_리스트_조회() {
        List<LanguageEntity> allByResumeId = repository.findAllByResumeId(1L);

        assertThat(allByResumeId).isEqualTo(List.of(LanguageEntity.builder().id(1L).name("TOEIC").acquisitionDate(LocalDateTime.of(2022, 11, 20, 10, 0, 5)).score("900").resume(resume).build(), LanguageEntity.builder().id(2L).name("TOEFL").acquisitionDate(LocalDateTime.of(2022, 11, 29, 10, 0, 5)).score("860").resume(resume).build()));
    }

}
