package darkoverload.itzip.feature.school.controller;

import darkoverload.itzip.feature.school.code.RegionType;
import darkoverload.itzip.feature.school.code.SchoolType;
import darkoverload.itzip.feature.school.controller.response.SearchResponse;
import darkoverload.itzip.feature.school.domain.School;
import darkoverload.itzip.feature.school.service.SchoolService;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schoolsearch")
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping("")
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    public ResponseEntity<SearchResponse> searchSchool(@RequestParam(value = "schoolName") String schoolName){
        // 학교정보를 10개 정도만 가져와 준다.
        SearchResponse searchResponse = schoolService.searchSchool(schoolName);

        return new ResponseEntity<>(searchResponse, HttpStatus.OK);
    }

}
