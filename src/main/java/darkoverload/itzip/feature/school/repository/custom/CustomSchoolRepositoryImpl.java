package darkoverload.itzip.feature.school.repository.custom;

import darkoverload.itzip.feature.school.entity.SchoolDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class CustomSchoolRepositoryImpl implements CustomSchoolRepository{

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<String> searchSchool(String schoolName) {
        final int size = 10;
        Criteria criteria = new Criteria("school_name").contains(schoolName);
        PageRequest pageRequest = PageRequest.of(0, size);
        CriteriaQuery query = new CriteriaQuery(criteria, pageRequest);

        // 검색 수행
        return elasticsearchOperations.search(query, SchoolDocument.class)
                .stream()
                .map(hit->hit.getContent().getSchoolName())
                .collect(Collectors.toList());
    }
}
