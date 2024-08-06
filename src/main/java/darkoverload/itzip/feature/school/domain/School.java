package darkoverload.itzip.feature.school.domain;

import darkoverload.itzip.feature.school.code.EstType;
import darkoverload.itzip.feature.school.code.RegionType;
import darkoverload.itzip.feature.school.code.SchoolType;
import darkoverload.itzip.feature.school.entity.SchoolEntity;
import darkoverload.itzip.feature.school.util.SchoolJsonUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

import static darkoverload.itzip.feature.school.util.SchoolJsonUtil.getSchoolInfo;


@Builder
@ToString
@Getter
public class School {
    private String schoolName;

    private String gubun;

    private SchoolType schoolType;

    private String address;

    private String campusName;

    private EstType estType;

    private RegionType region;


    private static final WebClientWrapper webClientWrapper = new WebClientWrapper(WebClient.builder());

    public SchoolEntity convertToEntity(){
        return SchoolEntity.builder()
                .schoolName(this.schoolName)
                .gubun(this.gubun)
                .schoolType(this.schoolType)
                .address(this.address)
                .campusName(this.campusName)
                .estType(this.estType)
                .region(this.region)
                .build();
    }

    public static List<SchoolEntity> getSchoolInfoData(String apiUrl, String apiKey, int page, int perPage, String gubun) {
        final WebClientWrapper webClientWrapper = new WebClientWrapper(WebClient.builder());
        String url = apiUrl + "?apiKey=" + apiKey + "&svcType=api&svcCode=SCHOOL&contentType=json&gubun=" + gubun + "&thisPage="+ page + "&perPage=" + perPage;
        String schools = webClientWrapper.get().uri(url).retrieve().bodyToMono(String.class).block();

        SchoolType schoolType = null;
        switch(gubun) {
            case "elem_list"-> schoolType = SchoolType.ELEMENTARY_SCHOOL;
            case "midd_list"-> schoolType = SchoolType.MIDDLE_SCHOOL;
            case "high_list"-> schoolType = SchoolType.HIGH_SCHOOL;
            case "univ_list"-> schoolType = SchoolType.UNIVERSITY;
            case "seet_list" -> schoolType = SchoolType.SPECIAL_SCHOOL;
        }

        return getSchoolInfo(schools, schoolType);
    }


    public static List<String> getTotalCount(String apiUrl, String apiKey, List<String> gubun_list) {
        // 학교정보 url 리스트 만들기
        List<String> makeUrl_list = new ArrayList<>();

        // makeUrl_list에 넣어준다.
        for(String gubun: gubun_list) makeUrl_list.add(apiUrl + "?apiKey=" + apiKey + "&svcType=api&svcCode=SCHOOL&contentType=json&gubun=" + gubun);

        // convertToList에 넣어준다.
        List<String> convertToList = new ArrayList<>();
        for(String makeUrl : makeUrl_list) convertToList.add(webClientWrapper.get().uri(makeUrl).retrieve().bodyToMono(String.class).block());

        // total_list 정보
        ArrayList<String> totalPages = new ArrayList<>();
        convertToList.forEach(json -> totalPages.add(SchoolJsonUtil.getTotalCount(json)));

        return totalPages;
    }
}
