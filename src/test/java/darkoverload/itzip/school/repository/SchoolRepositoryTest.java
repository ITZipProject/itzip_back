package darkoverload.itzip.school.repository;


import darkoverload.itzip.global.config.querydsl.TestQueryDslConfig;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import darkoverload.itzip.resume.repository.SchoolRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = {TestQueryDslConfig.class})
@SqlGroup({
        @Sql(value = "/sql/school/delete-school-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD),
        @Sql(value = "/sql/school/school-repository-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
})
public class SchoolRepositoryTest {
    @Autowired
    private SchoolRepository schoolRepository;

    @Test
    void 모든_데이터_삭제_테스트() {
        //given
        schoolRepository.deleteAll();

        // then
        // when
        assertThatThrownBy(() -> {
            schoolRepository.findById(1L).orElseThrow(()-> new RestApiException(CommonExceptionCode.SCHOOL_NOT_FOUND));
        }).isInstanceOf(RestApiException.class);

        assertThatThrownBy(() -> {
            schoolRepository.findById(2L).orElseThrow(()-> new RestApiException(CommonExceptionCode.SCHOOL_NOT_FOUND));
        }).isInstanceOf(RestApiException.class);
    }

    @Test
    void 데이터를_모두_카운팅_한다(){
        //given
        Long totalCount = schoolRepository.getTotalCount();

        // then
        // when
        assertThat(totalCount).isEqualTo(2L);
    }
}
