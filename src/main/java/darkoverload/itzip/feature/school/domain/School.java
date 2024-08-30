package darkoverload.itzip.feature.school.domain;

import darkoverload.itzip.feature.school.code.EstType;
import darkoverload.itzip.feature.school.code.RegionType;
import darkoverload.itzip.feature.school.code.SchoolType;
import darkoverload.itzip.feature.school.entity.SchoolDocument;
import darkoverload.itzip.feature.school.entity.SchoolEntity;
import darkoverload.itzip.feature.school.util.SchoolJsonUtil;
import darkoverload.itzip.global.config.webclient.WebClientWrapper;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.elasticsearch.core.suggest.Completion;
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

    public SchoolDocument covertToDocument(){

        Completion suggest = new Completion(new String[]{this.schoolName});

        return SchoolDocument.builder()
                .schoolNameSuggest(suggest)
                .schoolName(this.schoolName)
                .gubun(this.gubun)
                .schoolType(this.schoolType)
                .address(this.address)
                .campusName(this.campusName)
                .estType(this.estType)
                .region(this.region)
                .build();
    }

    /**
     * 모든 학교 정보를 가져온다
     * @param apiUrl api url 정보
     * @param apiKey api key 정보
     * @param page 페이지 개수
     * @param perPage  페이지당 호출할 데이터
     * @param gubun 학교 구분 정보
     * @return School 정보 엔티티 리스트
     */
    public static List<SchoolDocument> getSchoolInfoData(String apiUrl, String apiKey, int page, int perPage, String gubun) {
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

    /**
     * 학교 정보 총 개수를 api에서 가져와준다.
     * @param apiUrl url 정보
     * @param apiKey key 정보
     * @param gubun_list 학교 구분 리스트
     * @return 총 개수 리스트 정보 반환
     */
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
