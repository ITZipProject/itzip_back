package darkoverload.itzip.feature.job.controller;

import darkoverload.itzip.feature.job.controller.request.JobInfoScrapRequest;
import darkoverload.itzip.feature.job.controller.response.JobInfoSearchResponse;
import darkoverload.itzip.feature.job.service.JobInfoScrapRedisService;
import darkoverload.itzip.feature.job.service.JobInfoService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;
import darkoverload.itzip.global.config.swagger.SwaggerRequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/job-info")
@RequiredArgsConstructor
public class JobInfoController {

    private final JobInfoService jobInfoService;
    private final JobInfoScrapRedisService jobInfoScrapRedisService;

    @Operation(
            summary = "채용공고 조회",
            description = "채용공고 페이징 처리로 조회"
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.BAD_REQUEST)
    @GetMapping("")
    public Page<JobInfoSearchResponse> searchJobInfo(@Parameter(description = "검색어") @RequestParam(value = "search", required = false) String search, @Parameter(description = "기술 정보 필터") @RequestParam(value = "techName", required = false) String category, @Parameter(description = "경력 최소 값") @RequestParam(value = "experienceMin", required = false) Integer experienceMin, @Parameter(description = "지역정보") @RequestParam(required = false) String locationCode, @Parameter(description = "경력 최대 값") @RequestParam(value = "experienceMax", required = false) Integer experienceMax, @Parameter(description = "Size : 페이지당 출력할 항목의 개수 (기본값: 10) \n sort`: 정렬 기준 필드 (기본값: `scrap`) \n direction`: 정렬 순서 (기본값: 내림차순 `DESC`)") @PageableDefault(size = 10, sort = "scrap", direction = Sort.Direction.DESC) Pageable pageable) {

        return jobInfoService.searchJobInfo(search, category, experienceMin, experienceMax, locationCode, pageable);
    }

    @Operation(
            summary = "스크랩, 스크랩 취소"
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({CommonExceptionCode.JOB_INFO_NOT_FOUND, CommonExceptionCode.NOT_FOUND_USER})
    @PostMapping("/scrap")
    public String scrapJobInfo(@SwaggerRequestBody(description = "채용정보 스크립에 대한 정보", required = true, content = @Content(schema = @Schema(implementation = JobInfoScrapRequest.class)
    )) @RequestBody JobInfoScrapRequest request) {

        return jobInfoScrapRedisService.jobInfoScrapToRedis(request);
    }

}
