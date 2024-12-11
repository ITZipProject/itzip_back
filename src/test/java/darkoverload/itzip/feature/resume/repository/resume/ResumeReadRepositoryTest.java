package darkoverload.itzip.feature.resume.repository.resume;


import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.entity.ResumeEntity;
import darkoverload.itzip.global.config.querydsl.TestQueryDslConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@DataJpaTest
@SqlGroup({
        @Sql(value = "/sql/resume/delete-resume-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(value = "/sql/resume/resume-repository-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
@ContextConfiguration(classes = {TestQueryDslConfig.class})
@ActiveProfiles("test")
class ResumeReadRepositoryTest {

    @Autowired
    private ResumeReadJpaRepository repository;

    @Test
    void 이력서_페이징_조회() {
        PageRequest page = PageRequest.of(0, 10, Sort.by("modify_date").descending());

        List<ResumeEntity> resumes = repository.searchResumeInfos(null, page);

        assertThat(resumes).isEqualTo(List.of(
                new ResumeEntity(1L, 1L, "itzip@gmail.com", "https://itzip.com", "잇집 홍길동", "010-2355-9839", "잇집입니다.", List.of("잇집이력서"), PublicOnOff.YES, new ArrayList<>()),
                new ResumeEntity(2L, 2L, "park@gmail.com", "https://itzip.com", "잇집 박길동", "010-2354-4444", "park입니다.", List.of("park이력서"), PublicOnOff.YES, new ArrayList<>()),
                new ResumeEntity(3L, 3L, "sin@gmail.com", "https://itzip.com", "sin 홍길동", "010-2355-2331", "sin입니다.", List.of("sin이력서"), PublicOnOff.YES, new ArrayList<>()),
                new ResumeEntity(4L, 2L, "lev@gmail.com", "https://itzip.com", "lev 잇집 박길동", "010-2354-4444", "lev입니다.", List.of("lev이력서"), PublicOnOff.YES, new ArrayList<>())
        ));
    }

}