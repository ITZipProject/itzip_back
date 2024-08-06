package darkoverload.itzip.school;

import darkoverload.itzip.feature.school.code.RegionType;
import darkoverload.itzip.feature.school.code.SchoolType;
import darkoverload.itzip.feature.school.domain.School;
import darkoverload.itzip.feature.school.service.SchoolConnectServiceImpl;
import darkoverload.itzip.feature.school.util.SchoolJsonUtil;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ActiveProfiles(profiles = "test")
@TestPropertySource(locations = "classpath:application-test.yml")
@ExtendWith(MockitoExtension.class)
public class SchoolServiceTest {
    @InjectMocks
    SchoolConnectServiceImpl schoolConnectService;

    @Test
    void 총개수_페이지_변환_테스트(){
        List<String> numList = List.of("6342", "3782", "2439", "1987", "1372");
        Map <String, Integer> map = schoolConnectService.calculatePageCount(numList);


        assertAll(
                ()-> assertEquals(map.get("elem_list"), 13),
                ()-> assertEquals(map.get("midd_list"), 8),
                ()-> assertEquals(map.get("high_list"), 5),
                ()-> assertEquals(map.get("univ_list"), 4),
                ()-> assertEquals(map.get("seet_list"), 3)
        );

    }

    @Test
    void json데이터를_학교정보로_변경(){
        // given
        String json = "{\"dataSearch\":{\"content\":[{\"link\":\"http://gageodo.es.jne.kr/\",\"adres\":\"전라남도 신안군 흑산면 가거도길 18-31(흑산면)\",\"schoolName\":\"가거도초등학교\",\"region\":\"전라남도\",\"totalCount\":\"6328\",\"estType\":\"공립\",\"seq\":\"4491\"}]}}";

        // when
        School school = SchoolJsonUtil.getSchoolInfo(json, SchoolType.ELEMENTARY_SCHOOL).get(0).convertDomain();

        // then
        assertAll(
                ()-> assertEquals("전라남도 신안군 흑산면 가거도길 18-31(흑산면)", school.getAddress()),
                ()-> assertEquals("가거도초등학교", school.getSchoolName()),
                ()-> assertEquals(RegionType.JEOLLANAMDO, school.getRegion())
        );

    }

    @Test
    void json__학교_데이터_에러_체크() {
        // given
        String json = "\"dataSearch\":{\"content\":[{\"link\":\"http://gageodo.es.jne.kr/\",\"adres\":\"전라남도 신안군 흑산면 가거도길 18-31(흑산면)\",\"schoolName\":\"가거도초등학교\",\"region\":\"전라남도\",\"totalCount\":\"6328\",\"estType\":\"공립\",\"seq\":\"4491\"}]}";

        // when
        //then
        assertThatThrownBy(()->SchoolJsonUtil.getSchoolInfo(json, SchoolType.ELEMENTARY_SCHOOL).get(0).convertDomain()).isInstanceOf(RestApiException.class);
    }

}
