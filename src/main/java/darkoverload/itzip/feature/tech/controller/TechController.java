package darkoverload.itzip.feature.tech.controller;

import darkoverload.itzip.feature.tech.controller.response.GetTechResponse;
import darkoverload.itzip.feature.tech.domain.Tech;
import darkoverload.itzip.feature.tech.service.TechService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tech")
@RequiredArgsConstructor
public class TechController {

    private final TechService service;


    @Operation(
            summary = "사람인 API 기술 스택 정보 조회",
            description = "단순 조회 Parameter 값은 없음"
    )
    @GetMapping("")
    public List<GetTechResponse> getTechInfo(){

        return service.getTechInfo().stream().map(Tech::toGetTechResponse).collect(Collectors.toList());

    }

}
