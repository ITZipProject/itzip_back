//package darkoverload.itzip.feature.job.service.connect;
//
//import darkoverload.itzip.feature.job.domain.job.JobInfo;
//import darkoverload.itzip.feature.job.util.TimeStampUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.TestPropertySource;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@ActiveProfiles(profiles = "test")
//@TestPropertySource(locations = "classpath:application-test.yml")
//@ExtendWith(MockitoExtension.class)
//public class JobInfoConnectServiceTest {
//
//    @InjectMocks
//    private JobInfoConnectServiceImpl service;
//
//    List<JobInfo> apiData = new ArrayList<>();
//    List<JobInfo> dbData = new ArrayList<>();
//
//    @BeforeEach
//    void setUp() {
//        JobInfo jobInfoDbDataFirst = JobInfo.builder()
//                .active("1")
//                .closeTypeCode("1")
//                .closeTypeCode("접수마감일")
//                .companyHref("http://www.saramin.co.kr/zf_user/company-info/view?csn=2208134781&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api")
//                .companyName("(주)이수시스템")
//                .experienceCode("2")
//                .experienceMin(5L)
//                .experienceMax(10L)
//                .experienceName("경력 5~10년")
//                .expirationDate(TimeStampUtil.convertToLocalDateTime("1725448097"))
//                .industryCode("301")
//                .industryName("솔루션·SI·ERP·CRM")
//                .jobCode("84,87,91,92,95,216,235,236,239,240,256,257,263,291,292,293,312,89,118,136,149,156,235,236,239,240,256,257,291,312")
//                .jobMidCode("2")
//                .jobMidName("IT개발·데이터")
//                .jobName("소프트웨어개발,백엔드/서버개발,웹개발,퍼블리셔,프론트엔드,DBA,ECMAScript,Java,Javascript,jQuery,JSP,MyBatis,MySQL,OracleDB,Spring,SpringBoot,SQL,Vue.js,유지보수,솔루션,클라우드,ERP,IoT")
//                .jobTypeCode("1")
//                .jobTypeName("정규직")
//                .keyword("소프트웨어개발")
//                .locationCode("101000,101150")
//                .locationName("서울 &gt; 서울전체,서울 &gt; 서초구")
//                .positionId(48953038L)
//                .expirationDate(TimeStampUtil.convertToLocalDateTime("1726757999"))
//                .requiredEducationCode("8")
//                .requiredEducationName("대학교졸업(4년)이상")
//                .salaryCode("0")
//                .salaryName("회사내규에 따름")
//                .title("Cloud 인사시스템 개발자")
//                .url("http://www.saramin.co.kr/zf_user/jobs/relay/view?rec_idx=48953038&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api")
//                .build();
//
//        JobInfo jobInfoDbDataSecond = JobInfo.builder()
//                .active("1")
//                .closeTypeCode("1")
//                .closeTypeCode("접수마감일")
//                .companyHref("http://www.saramin.co.kr/zf_user/company-info/view?csn=2118848320&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api")
//                .companyName("(주)넥스트테크놀로지")
//                .experienceCode("1")
//                .experienceMin(0L)
//                .experienceMax(0L)
//                .experienceName("신입")
//                .expirationDate(TimeStampUtil.convertToLocalDateTime("1725448097"))
//                .industryCode("308")
//                .industryName("정보보안·백신")
//                .jobCode("90,715,719,751,796,798,2203")
//                .jobMidCode("2,8")
//                .jobMidName("IT개발·데이터,영업·판매·무역")
//                .jobName("IDS·IPS,네트워크보안,방화벽,보안,정보보안,네트워크영업,솔루션기술영업,IT영업,H/W,S/W,영업")
//                .jobTypeCode("1")
//                .jobTypeName("정규직")
//                .keyword("IDS·IPS,네트워크보안,방화벽,보안,정보보안")
//                .locationCode("101000")
//                .locationName("서울 &gt; 서울전체")
//                .positionId(48953031L)
//                .expirationDate(TimeStampUtil.convertToLocalDateTime("1726757999"))
//                .requiredEducationCode("7")
//                .requiredEducationName("대학졸업(2,3년)이상")
//                .salaryCode("99")
//                .salaryName("면접후 결정")
//                .title("보안솔루션 및 네트워크장비 영업")
//                .url("http://www.saramin.co.kr/zf_user/jobs/relay/view?rec_idx=48953031&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api")
//                .build();
//
//
//
//        dbData.addAll(List.of(jobInfoDbDataFirst, jobInfoDbDataSecond));
//
//        JobInfo jobInfoApiDataFirst = JobInfo.builder()
//                .active("1")
//                .closeTypeCode("1")
//                .closeTypeCode("접수마감일")
//                .companyHref("http://www.saramin.co.kr/zf_user/company-info/view?csn=2208134781&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api")
//                .companyName("(주)이수")
//                .experienceCode("2")
//                .experienceMin(2L)
//                .experienceMax(10L)
//                .experienceName("경력 2~10년")
//                .expirationDate(TimeStampUtil.convertToLocalDateTime("1725448097"))
//                .industryCode("301")
//                .industryName("솔루션·SI·ERP·CRM")
//                .jobCode("84,87,91,92,95,216,235,236,239,240,256,257,263,291,292,293,312,89,118,136,149,156,235,236,239,240,256,257,291,312")
//                .jobMidCode("2")
//                .jobMidName("IT개발·데이터")
//                .jobName("소프트웨어개발,백엔드/서버개발,웹개발,퍼블리셔,프론트엔드,DBA,ECMAScript,Java,Javascript,jQuery,JSP,MyBatis,MySQL,OracleDB,Spring,SpringBoot,SQL,Vue.js,유지보수,솔루션,클라우드,ERP,IoT")
//                .jobTypeCode("1")
//                .jobTypeName("계약직")
//                .keyword("소프트웨어개발")
//                .locationCode("101000,101150")
//                .locationName("서울 &gt; 서울전체,서울 &gt; 서초구")
//                .positionId(48953038L)
//                .expirationDate(TimeStampUtil.convertToLocalDateTime("1726757999"))
//                .requiredEducationCode("8")
//                .requiredEducationName("대학교졸업(4년)이상")
//                .salaryCode("0")
//                .salaryName("회사내규에 따름")
//                .title("백엔드 인사시스템 개발자")
//                .url("http://www.saramin.co.kr/zf_user/jobs/relay/view?rec_idx=48953038&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api")
//                .build();
//
//        JobInfo jobInfoApiDataSecond = JobInfo.builder()
//                .active("1")
//                .closeTypeCode("1")
//                .closeTypeCode("접수마감일")
//                .companyHref("http://www.saramin.co.kr/zf_user/company-info/view?csn=8558702525&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api")
//                .companyName("(주)다담서포트")
//                .experienceCode("2")
//                .experienceMin(3L)
//                .experienceMax(15L)
//                .experienceName("경력 3~15년")
//                .expirationDate(TimeStampUtil.convertToLocalDateTime("1725448097"))
//                .industryCode("304")
//                .industryName("쇼핑몰·오픈마켓")
//                .jobCode("209,1483,1487,1490,1501,1504,1513,1600,1611,1614,1981,291")
//                .jobMidCode("2,15,21")
//                .jobMidName("IT개발·데이터,디자인,고객상담·TM")
//                .jobName("쇼핑몰,오픈마켓,전자상거래,CSS,광고디자인,로고디자인,문구디자인,완구디자인,일러스트레이터,컨셉디자인,일러스트,HTML,PhotoShop,고객관리,Spring")
//                .jobTypeCode("1")
//                .jobTypeName("정규직")
//                .keyword("쇼핑몰,오픈마켓,전자상거래")
//                .locationCode("101040")
//                .locationName("서울 &gt; 강서구")
//                .positionId(48952926L)
//                .expirationDate(TimeStampUtil.convertToLocalDateTime("1726757999"))
//                .requiredEducationCode("0")
//                .requiredEducationName("학력무관")
//                .salaryCode("99")
//                .salaryName("면접후 결정")
//                .title("[베스트냅킨] 일회용품 쇼핑몰 편집 디자이너 모집")
//                .url("http://www.saramin.co.kr/zf_user/jobs/relay/view?rec_idx=48952926&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api")
//                .build();
//
//        apiData.addAll(List.of(jobInfoApiDataFirst, jobInfoApiDataSecond));
//    }
//
//
//    @Test
//    void 채용정보_저장_리스트_성공(){
//
//        // given
//        List<JobInfo> jobInfoEntities = service.makeSaveList(apiData, dbData);
//
//        // when
//        JobInfo jobInfo = jobInfoEntities.getFirst();
//
//        // then
//        assertEquals(jobInfo.getPositionId(),48952926L);
//
//    }
//
//    @Test
//    void 채용정보_삭제_리스트_성공() {
//
//        // given
//        List<Long> deleteList = service.makeDeleteList(apiData, dbData);
//
//        // when
//        Long positionId = deleteList.getFirst();
//
//        // then
//        assertEquals(positionId, 48953031L);
//    }
//
//    @Test
//    void 채용정보_업데이트_리스트_성공(){
//        // given
//        List<JobInfo> jobInfoEntities = service.makeUpdateList(apiData, dbData);
//
//        // when
//        JobInfo jobInfo = jobInfoEntities.getFirst();
//
//        // then
//        assertAll(
//                ()-> assertEquals(jobInfo.getJobTypeName(), "계약직"),
//                () -> assertEquals(jobInfo.getCompanyName(), "(주)이수"),
//                () -> assertEquals(jobInfo.getExperienceName(), "경력 2~10년")
//        );
//
//    }
//}
