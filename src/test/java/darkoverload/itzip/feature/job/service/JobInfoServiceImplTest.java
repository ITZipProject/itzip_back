//package darkoverload.itzip.feature.job.service;
//
//import darkoverload.itzip.feature.job.controller.request.JobInfoScrapRequest;
//import darkoverload.itzip.feature.job.domain.job.JobInfo;
//import darkoverload.itzip.feature.job.domain.scrap.JobInfoScrap;
//import darkoverload.itzip.feature.job.repository.JobInfoRepository;
//import darkoverload.itzip.feature.job.repository.JobInfoScrapRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.jdbc.SqlGroup;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.assertAll;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@Slf4j
//@SqlGroup({
//        @Sql(value = "/sql/jobinfo/scrap/infoscrap-repository-date.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
//        @Sql(value = "/sql/jobinfo/scrap/delete-infoscrap-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//})
//@Transactional
//@SpringBootTest
//@ActiveProfiles("test")
//class JobInfoServiceImplTest {
//
//    @Autowired
//    private JobInfoService jobInfoService;
//    @Autowired
//    private JobInfoRepository jobInfoRepository;
//    @Autowired
//    private JobInfoScrapRepository jobInfoScrapRepository;
//
//    @Test
//    void 채용정보_스크랩() {
//
//        // given
//        JobInfoScrapRequest request = new JobInfoScrapRequest(13L, "test@test.com");
//
//        // when
//        String response = jobInfoService.jobInfoScrap(request);
//        JobInfoScrap jobInfoScrap = jobInfoScrapRepository.findByJobInfoId(13L, "test@test.com").get();
//        JobInfo jobInfoEntity = jobInfoRepository.getReferenceById(13L);
//
//        // then
//        assertAll(
//                () -> assertEquals(response, "채용정보 스크랩을 하였습니다."),
//                () -> assertEquals(jobInfoScrap.getJobInfo().getId(), 13),
//                () -> assertEquals(jobInfoScrap.getUser().getId(), 100),
//                () -> assertEquals(jobInfoEntity.getScrapCount(), 1)
//        );
//    }
//
//    @Test
//    void 채용정보_스크랩_해제() {
//
//        // given
//        JobInfoScrapRequest request = new JobInfoScrapRequest(12L, "test@test.com");
//
//        //when
//        String response = jobInfoService.jobInfoScrap(request);
//
//        // then
//        assertEquals(response, "채용정보 스크랩을 취소하였습니다.");
//
//    }
//}