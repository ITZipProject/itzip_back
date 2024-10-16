package darkoverload.itzip.feature.resume.controller;

import darkoverload.itzip.feature.jwt.infrastructure.CustomUserDetails;
import darkoverload.itzip.feature.resume.controller.request.CreateResumeRequest;
import darkoverload.itzip.feature.resume.controller.request.UpdateResumeRequest;
import darkoverload.itzip.feature.resume.controller.response.UpdateResumeResponse;
import darkoverload.itzip.feature.resume.service.resume.ResumeService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;
import darkoverload.itzip.global.config.swagger.SwaggerRequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService service;


    @Operation(
            summary = "이력서 생성",
            description = "이력서 생성 시 객체 리스트에 존재하는 값만 validation check"
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.BAD_REQUEST)
    @PostMapping("")
    public String createResume(@Valid @RequestBody CreateResumeRequest request){

        service.create(request);

        return "이력서 저장을 성공하였습니다.";
    }

    @Operation(
            summary = "이력서 수정",
            description = "이력서 수정 시 객체 리스트에 존재하는 값 체크"
    )
    @PatchMapping("")
    public String updateResume(@SwaggerRequestBody(description = "이력서 수정 정보", content= @Content(
            schema = @Schema(implementation = UpdateResumeRequest.class)
    )) @Valid @RequestBody UpdateResumeRequest request) {

        service.update(request);

        return "이력서 수정을 성공하였습니다.";
    }

}
