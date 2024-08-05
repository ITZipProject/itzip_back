package darkoverload.itzip.school;

import darkoverload.itzip.school.domain.School;
import darkoverload.itzip.school.entity.SchoolEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import java.util.List;
import static darkoverload.itzip.school.domain.School.getSchoolInfoData;
import static org.assertj.core.api.Assertions.*;


@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class SchoolWebClientTest {

    @Value("${school.api-url}")
    private String apiUrl;

    @Value("${school.api-key}")
    private String apiKey;

    @Test
    void 학교_정보_외부API를_가져온다() {
        //given
        List<SchoolEntity> list = getSchoolInfoData(apiUrl, apiKey, 1, 1, "elem_list");
        //when
        School school = list.getFirst().convertDomain();
        //then
        assertThat(school).isInstanceOf(School.class);
    }

    @Test
    void 학교_정보_API_(){

        // given
        // when
        // then
        assertThatThrownBy(()-> getSchoolInfoData("dasdsadsadsa","adsdsadsa",1,1,"elem_list")).isInstanceOf(WebClientRequestException.class);

    }

    @Test
    void 학교_정보_전체_개수(){
        // given
        List<String> gubun_list = List.of("elem_list", "midd_list", "high_list", "univ_list", "seet_list");

        // when
        // then
        assertThat(School.getTotalCount(apiUrl, apiKey, gubun_list)).isInstanceOf(List.class);

    }

    @Test
    void 학교_정보_전체_개수_에러_체크(){
        // given
        List<String> gubun_list = List.of("elem_list", "midd_list", "high_list", "univ_list", "seet_list");

        assertThatThrownBy(()-> School.getTotalCount("http://gfgggg.com", "dskmadsa", gubun_list)).isInstanceOf(WebClientRequestException.class);
    }
}
