package darkoverload.itzip.feature.resume.controller;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.resume.controller.request.CreateResumeRequest;
import darkoverload.itzip.feature.resume.controller.request.UpdateResumeRequest;
import darkoverload.itzip.feature.resume.controller.response.CreateResumeResponse;
import darkoverload.itzip.feature.resume.controller.response.SearchResumeResponse;
import darkoverload.itzip.feature.resume.controller.response.UpdateResumeResponse;
import darkoverload.itzip.feature.resume.service.resume.ResumeReadService;
import darkoverload.itzip.feature.resume.service.resume.ResumeService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;
import darkoverload.itzip.global.config.swagger.SwaggerRequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService service;
    private final ResumeReadService resumeReadService;

    @Operation(
            summary = "이력서 생성",
            description = "이력서 생성 시 객체 리스트에 존재하는 값만 validation check"
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.BAD_REQUEST)
    @PostMapping("")
    public CreateResumeResponse createResume(@Valid @RequestBody CreateResumeRequest request) {

        return service.create(request);
    }

    @Operation(
            summary = "이력서 수정",
            description = "이력서 수정 시 객체 리스트에 존재하는 값 체크"
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.BAD_REQUEST)
    @PatchMapping("")
    public UpdateResumeResponse updateResume(@SwaggerRequestBody(description = "이력서 수정 정보", content = @Content(
            schema = @Schema(implementation = UpdateResumeRequest.class)
    )) @Valid @RequestBody UpdateResumeRequest request, @AuthenticationPrincipal CustomUserDetails user) {

        return service.update(request, user);
    }

    @Operation(
            summary = "이력서 전체 조회",
            description = "사용자 이력서 전체 조회"
    )
    @GetMapping("/search")
    public Page<SearchResumeResponse> searchResume(@Parameter(description = "검색어") @RequestParam(value = "search", required = false) String search, @Parameter(description = "Size : 페이지당 출력할 항목의 개수 (기본값: 10) \n sort`: 정렬 기준 필드 (기본값: `modifyDate`) \n direction`: 정렬 순서 (기본값: 내림차순 `DESC`)") @PageableDefault(size= 10, sort="modifyDate", direction = Sort.Direction.DESC)Pageable pageable){
        List<SearchResumeResponse> searchResumeResponses = resumeReadService.searchResumeInfos(search, pageable);

        return new PageImpl<>(searchResumeResponses, pageable,searchResumeResponses.size());
    }

}
