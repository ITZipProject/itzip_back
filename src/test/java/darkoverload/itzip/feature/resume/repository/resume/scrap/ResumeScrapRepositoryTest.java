package darkoverload.itzip.feature.resume.repository.resume.scrap;


import darkoverload.itzip.feature.resume.code.PublicOnOff;
import darkoverload.itzip.feature.resume.domain.resume.scrap.ResumeScrap;
import darkoverload.itzip.feature.resume.entity.ProfileInfoEntity;
import darkoverload.itzip.feature.resume.entity.resume.ResumeEntity;
import darkoverload.itzip.feature.user.entity.Authority;
import darkoverload.itzip.feature.user.entity.UserEntity;
import darkoverload.itzip.global.config.querydsl.TestQueryDslConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@DataJpaTest
@SqlGroup({
        @Sql(value = "/sql/resume/scrap/resume-scrap-repository-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/resume/scrap/delete-resume-scrap-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
@ContextConfiguration(classes = {TestQueryDslConfig.class})
@ActiveProfiles("test")
public class ResumeScrapRepositoryTest {

    @Autowired
    private ResumeScrapJpaRepository repository;

    @Test
    void 내가_스크랩_한_이력서_확인() {
        Optional<ResumeScrap> resumeScrap = repository.findByResumeScrap(1L, 1L);

        assertThat(resumeScrap.get())
                .isEqualTo(ResumeScrap.builder()
                .id(1L)
                .user(
                        UserEntity.builder()
                                .id(1L)
                                .authority(Authority.USER)
                                .email("itzip@gmail.com")
                                .nickname("doudoudou")
                                .password("asdfg1234")
                                .imageUrl("https://itzip.com")
                                .build()
                )
                .resume(
                        ResumeEntity.builder()
                                .id(1L)
                                .userId(1L)
                                .imageUrl("https://itzip.com")
                                .links(List.of("잇집이력서"))
                                .profileInfo(
                                        ProfileInfoEntity.builder()
                                                .email("itzip@gmail.com")
                                                .subject("잇집 홍길동")
                                                .introduction("잇집입니다.")
                                                .phone("010-2355-9839")
                                                .publicOnOff(PublicOnOff.YES)
                                                .build()
                                )
                                .fileUrls(List.of())
                                .scrapCount(0)
                                .build()
                )
                .build());
    }

}
