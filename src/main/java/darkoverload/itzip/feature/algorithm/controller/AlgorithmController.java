package darkoverload.itzip.feature.algorithm.controller;

import darkoverload.itzip.feature.algorithm.controller.response.ProblemListResponse;
import darkoverload.itzip.feature.algorithm.controller.response.SolvedUserResponse;
import darkoverload.itzip.feature.algorithm.service.AlgorithmService;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.code.CommonResponseCode;
import darkoverload.itzip.global.config.swagger.ExceptionCodeAnnotations;
import darkoverload.itzip.global.config.swagger.ResponseCodeAnnotation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Algorithm Problem Recommend", description = "코딩 테스트 문제 추천 서비스")
@RestController
@RequestMapping("algorithm")
@RequiredArgsConstructor
public class AlgorithmController {
    private final AlgorithmService algorithmService;

    @Operation(
            summary = "문제 추천 받기",
            description = "tag아이디를 포함안하면 사용자 rating으로만 tagid를 포함하면 tag도 관련된 문제를 추천해줌"
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.NOT_FOUND_SOLVEDAC_USER)
    @GetMapping("/problems")
    public ProblemListResponse findProblemsByUserOrTag(
            @RequestParam Long userId,
            @RequestParam(required = false) Long tagId
    ){
        if (tagId == null) {
            return algorithmService.findProblemsByUser(userId);
        }
        return algorithmService.findProblemsByTagAndUser(userId, tagId);
    }

    @Operation(
            summary = "사용자 프로필 정보 받아오기",
            description = "사용자 정보를 받아오는 용도 업데이트를 하지 않으면 같은 정보만 받아올 수 있다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.NOT_FOUND_SOLVEDAC_USER)
    @GetMapping("/user")
    public SolvedUserResponse findSolvedUser(@RequestParam Long userId){
        return algorithmService.findUserSolvedProfile(userId);
    }

    @Operation(
            summary = "사용자 프로필 정보 생성하기",
            description = "사용자의 solvedac 닉네임을 받아와서 사용자 정보로 만들어준다."
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations(CommonExceptionCode.SOLVEDAC_API_ERROR)
    @PostMapping("/user")
    public void saveUserSolvedProfile(
            @RequestParam String username,
            @RequestParam Long userId
    ){
        algorithmService.saveUserSolvedProfile(userId, username);
    }

    @Operation(
            summary = "사용자 프로필 정보 업데이트하기",
            description = "사용자가 푼 문제 및 사용자 프로필의 정보를 업데이트 할수 있는 기능" +
                    "지금은 10초에 한번씩가능하지만 나중에 6시간으로 변경할 예정"
    )
    @ResponseCodeAnnotation(CommonResponseCode.SUCCESS)
    @ExceptionCodeAnnotations({
            CommonExceptionCode.NOT_FOUND_SOLVEDAC_USER,
            CommonExceptionCode.SOLVEDAC_API_ERROR
    })
    @GetMapping("/user/update")
    public SolvedUserResponse updateUserSolvedProfile(@RequestParam Long userId){
        return algorithmService.updateUserSolvedProfileAndProblem(userId);
    }
}
