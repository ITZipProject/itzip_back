package darkoverload.itzip.feature.school.service;

import darkoverload.itzip.feature.school.code.RegionType;
import darkoverload.itzip.feature.school.code.SchoolType;
import darkoverload.itzip.feature.school.controller.response.SearchResponse;
import darkoverload.itzip.feature.school.domain.School;
import darkoverload.itzip.feature.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService{

    private final SchoolRepository schoolRepository;

    /**
     * 학교 이름 기준으로 학교 정보 이름을 가져온다
     * @param schoolName 학교 이름
     * @return SearchResponse 학교 정보 응답 값
     */
    @Override
    public SearchResponse searchSchool(String schoolName) {
        // 학교 정보 DB에서 조회하여 가져오는 부분 10개만 가져옴
        List<String> schooList = schoolRepository.searchBySchoolName(schoolName);

        SearchResponse response = SearchResponse.builder()
                .schoolList(schooList)
                .build();

        return response;
    }
}
