package darkoverload.itzip.feature.job.controller;

import darkoverload.itzip.feature.job.controller.response.JobInfoSearchResponse;
import darkoverload.itzip.feature.job.service.JobInfoService;
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

    @GetMapping("")
    public Page<JobInfoSearchResponse> searchJobInfo(@RequestParam(value="search", required = false) String search, @RequestParam(value="techName", required = false) String category, @RequestParam(value="experienceMin", required = false) Integer experienceMin, @RequestParam(value="experienceMax", required = false) Integer experienceMax, @PageableDefault(size = 10, sort="scrap",direction = Sort.Direction.DESC)Pageable pageable) {

        return jobInfoService.searchJobInfo(search, category, experienceMin, experienceMax ,pageable);
    }

}
