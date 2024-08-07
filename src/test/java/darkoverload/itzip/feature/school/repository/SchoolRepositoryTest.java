package darkoverload.itzip.feature.school.repository;


import darkoverload.itzip.feature.school.repository.SchoolRepository;
import darkoverload.itzip.global.config.querydsl.TestQueryDslConfig;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@ContextConfiguration(classes = {TestQueryDslConfig.class})
@ActiveProfiles("test")
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

    @Test
    void 학교정보_api해당_이름_관련_리스트를_가져온다(){

        //given

        List<String> schoolList = schoolRepository.searchBySchoolName("가");

        //when
        //then
        assertAll(()-> assertThat(schoolList.getFirst()).isEqualTo("가거도초등학교"),
                ()->assertThat(schoolList.getLast()).isEqualTo("가경초등학교")
        );

    }

}
