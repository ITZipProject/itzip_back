package darkoverload.itzip.feature.school.service;

import darkoverload.itzip.feature.school.domain.School;
import darkoverload.itzip.feature.school.domain.WebClientWrapper;
import darkoverload.itzip.feature.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<String> totalCountList = School.getTotalCount(apiUrl, apiKey, gubun_list);
        Map<String, Integer> calculateMap = calculatePageCount(totalCountList);

        for(String gubun:gubun_list) {
            int pages = calculateMap.get(gubun);
            for(int i=1; i<=pages; i++){
                schoolRepository.saveAll(School.getSchoolInfoData(apiUrl, apiKey, i, 500, gubun));
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

    @Override
    public void deleteAll() {
        schoolRepository.deleteAll();
    }

    @Override
    public Long getTotalCount() {
        return schoolRepository.getTotalCount();
    }
}
