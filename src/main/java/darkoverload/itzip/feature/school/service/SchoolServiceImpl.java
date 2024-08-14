package darkoverload.itzip.feature.school.service;


import darkoverload.itzip.feature.school.controller.response.SearchResponse;
import darkoverload.itzip.feature.school.entity.SchoolDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService{

    private final ElasticsearchOperations elasticsearchOperations;

    /**
     * 학교 이름 기준으로 학교 정보 이름을 가져온다
     * @param schoolName 학교 이름
     * @return SearchResponse 학교 정보 응답 값
     */
    @Override
    public SearchResponse searchSchool(String schoolName) {
        final int size = 10;
        Criteria criteria = new Criteria("school_name").contains(schoolName);
        PageRequest pageRequest = PageRequest.of(0, size);
        CriteriaQuery query = new CriteriaQuery(criteria, pageRequest);

        // 검색 수행
        List<String> schoolList = elasticsearchOperations.search(query, SchoolDocument.class)
                .stream()
                .map(hit->hit.getContent().getSchoolName())
                .collect(Collectors.toList());

        return SearchResponse.builder()
                .schoolList(schoolList)
                .build();
    }
}
