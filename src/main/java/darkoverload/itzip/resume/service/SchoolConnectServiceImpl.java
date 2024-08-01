package darkoverload.itzip.resume.service;

import darkoverload.itzip.resume.code.SchoolType;
import darkoverload.itzip.resume.domain.WebClientWrapper;
import darkoverload.itzip.resume.entity.SchoolEntity;
import darkoverload.itzip.resume.repository.SchoolRepository;
import darkoverload.itzip.resume.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static darkoverload.itzip.resume.util.JsonUtil.getSchoolInfo;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchoolConnectServiceImpl implements SchoolConnectService {

    private final SchoolRepository schoolRepository;
    private final WebClientWrapper webClientWrapper = new WebClientWrapper(WebClient.builder());

    @Value("${school.api-url}")
    private String apiUrl;

    @Value("${school.api-key}")
    private String apiKey;

    private final List<String> gubun_list = List.of("elem_list", "midd_list", "high_list", "univ_list", "seet_list");

    @Override
    public void connectSchoolApi() {

        // 실제 호출할 url_list가 담겨 있다. 순서는 gubun_list 동일하다.
        List<String> totalCountList = getTotalCount(apiUrl, apiKey);
        Map<String, Integer> calculateMap = calculatePageCount(totalCountList);

        for(String gubun:gubun_list) {
            int pages = calculateMap.get(gubun);
            for(int i=1; i<=pages; i++){
                schoolRepository.saveAll(getSchoolInfoData(apiUrl, apiKey, i, 500, gubun));
            }
        }
    }

    public Map<String,Integer> calculatePageCount(List<String> totalCountList){
        Map<String, Integer> calcualteMap = new HashMap<>();
        for(int i=0; i<totalCountList.size(); i++) {
            int count = Integer.parseInt(totalCountList.get(i));
            int totalPage = count / 500 + 1;
            calcualteMap.put(gubun_list.get(i), totalPage);
        }

        return calcualteMap;
    }


    private List<String> getTotalCount(String apiUrl, String apiKey) {
        // 학교정보 url 리스트 만들기
        List<String> makeUrl_list = new ArrayList<>();

        // makeUrl_list에 넣어준다.
        for(String gubun: gubun_list) makeUrl_list.add(apiUrl + "?apiKey=" + apiKey + "&svcType=api&svcCode=SCHOOL&contentType=json&gubun=" + gubun);

        // convertToList에 넣어준다.
        List<String> convertToList = new ArrayList<>();
        for(String makeUrl : makeUrl_list) convertToList.add(webClientWrapper.get().uri(makeUrl).retrieve().bodyToMono(String.class).block());

        // total_list 정보
        ArrayList<String> totalPages = new ArrayList<>();
        convertToList.forEach(json -> totalPages.add(JsonUtil.getTotalCount(json)));

        return totalPages;
    }


    private List<SchoolEntity> getSchoolInfoData(String apiUrl, String apiKey, int page, int perPage, String gubun) {
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

    @Override
    public void deleteAll() {
        schoolRepository.deleteAll();
    }

    @Override
    public Long getTotalCount() {
        return schoolRepository.getTotalCount();
    }
}
