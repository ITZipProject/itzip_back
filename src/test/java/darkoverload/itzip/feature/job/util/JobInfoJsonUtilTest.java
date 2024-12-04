package darkoverload.itzip.feature.job.util;

import darkoverload.itzip.feature.job.domain.job.JobInfo;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles(profiles = "test")
@TestPropertySource(locations = "classpath:application-test.yml")
@ExtendWith(MockitoExtension.class)
class JobInfoJsonUtilTest {

    private static String json;
    private static String error_json;
        @BeforeAll
        static void setUp() {
            json = "{\"jobs\": {\"count\": 1,\"start\": 0,\"total\": \"7493\",\"job\": [{\"url\": \"http://www.saramin.co.kr/zf_user/jobs/relay/view?rec_idx=48954525&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api\",\"active\": 1,\"company\": {\"detail\": {\"href\": \"http://www.saramin.co.kr/zf_user/company-info/view?csn=1448116437&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api\",\"name\": \"(주)에이티지\"}},\"position\": {\"title\": \"(주)에이티지(대전연구소) 서버 개발자 경력자 채용\",\"industry\": {\"code\": \"301\",\"name\": \"솔루션·SI·ERP·CRM\"},\"location\": {\"code\": \"105000,105010,105020,105030,105040,118000\",\"name\": \"대전 &gt; 대전전체,대전 &gt; 대덕구,대전 &gt; 동구,대전 &gt; 서구,대전 &gt; 유성구,세종 &gt; 세종특별자치시\"},\"job-type\": {\"code\": \"1\",\"name\": \"정규직\"},\"job-mid-code\": {\"code\": \"2\",\"name\": \"IT개발·데이터\"},\"job-code\": {\"code\": \"84,108,109,217,222,235,244,254,270,292,108,167,197,241,254,270,291\",\"name\": \"소프트웨어개발,솔루션업체,백엔드/서버개발,딥러닝,머신러닝,ElasticStack,Git,Java,Kubernetes,MongoDB,PostgreSQL,SpringBoot,S/W,Apache,Kafka,Spring\"},\"experience-level\": {\"code\": 2,\"min\": 3,\"max\": 0,\"name\": \"경력3년↑\"},\"required-education-level\": {\"code\": \"8\",\"name\": \"대학교졸업(4년)이상\"}},\"keyword\": \"소프트웨어개발,솔루션업체\",\"salary\": {\"code\": \"99\",\"name\": \"면접후 결정\"},\"id\": \"48954525\",\"posting-timestamp\": \"1725438037\",\"modification-timestamp\": \"1725438128\",\"opening-timestamp\": \"1725436800\",\"expiration-timestamp\": \"1988118000\",\"close-type\": {\"code\": \"2\",\"name\": \"채용시\"}}]}}";

            error_json = "{\"jobs\": {\"\": 1,\"start\": 0,\"job\": [{\"url\": \"http://www.saramin.co.kr/zf_user/jobs/relay/view?rec_idx=48954525&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api\",\"active\": 1,\"company\": {\"detail\": {\"href\": \"http://www.saramin.co.kr/zf_user/company-info/view?csn=1448116437&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api\",\"name\": \"(주)에이티지\"}},\"position\": {\"title\": \"(주)에이티지(대전연구소) 서버 개발자 경력자 채용\",\"industry\": {\"code\": \"301\",\"name\": \"솔루션·SI·ERP·CRM\"},\"location\": {\"code\": \"105000,105010,105020,105030,105040,118000\",\"name\": \"대전 &gt; 대전전체,대전 &gt; 대덕구,대전 &gt; 동구,대전 &gt; 서구,대전 &gt; 유성구,세종 &gt; 세종특별자치시\"},\"job-type\": {\"code\": \"1\",\"name\": \"정규직\"},\"job-mid-code\": {\"code\": \"2\",\"name\": \"IT개발·데이터\"},\"job-code\": {\"code\": \"84,108,109,217,222,235,244,254,270,292,108,167,197,241,254,270,291\",\"name\": \"소프트웨어개발,솔루션업체,백엔드/서버개발,딥러닝,머신러닝,ElasticStack,Git,Java,Kubernetes,MongoDB,PostgreSQL,SpringBoot,S/W,Apache,Kafka,Spring\"},\"experience-level\": {\"code\": 2,\"min\": 3,\"max\": 0,\"name\": \"경력3년↑\"},\"required-education-level\": {\"code\": \"8\",\"name\": \"대학교졸업(4년)이상\"}},\"keyword\": \"소프트웨어개발,솔루션업체\",\"salary\": {\"code\": \"99\",\"name\": \"면접후 결정\"},\"id\": \"48954525\",\"posting-timestamp\": \"1725438037\",\"modification-timestamp\": \"1725438128\",\"opening-timestamp\": \"1725436800\",\"expiration-timestamp\": \"1988118000\",\"close-type\": {\"code\": \"2\",\"name\": \"채용시\"}}]}}";
        }


        @Test
        void 총_데이터_개수_구하기_성공() {

            // given
            // when
            int totalCount = JobInfoJsonUtil.getTotalCount(json);

            // then
            assertEquals(totalCount, 7493);
        }

    @Test
    void 총_데이터_개수_에러() {
        // given
        // when
        // then
        assertThatThrownBy(()-> JobInfoJsonUtil.getTotalCount(error_json)).isInstanceOf(RestApiException.class);
    }


        @Test
        void 학교정보데이터_변환_성공(){
            // given

            // when
            List<JobInfo> jobInfos = JobInfoJsonUtil.getInfoData(json);

            JobInfo jobInfo = jobInfos.getFirst();
                    // then
            assertNotNull(jobInfos);
            assertEquals(1, jobInfos.size());

            assertAll(
                    ()-> assertEquals(jobInfo.getUrl(), "http://www.saramin.co.kr/zf_user/jobs/relay/view?rec_idx=48954525&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api"),
                    () -> assertEquals(jobInfo.getActive(), "1"),
                    () -> assertEquals(jobInfo.getCompanyHref(), "http://www.saramin.co.kr/zf_user/company-info/view?csn=1448116437&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api"),
                    () -> assertEquals(jobInfo.getCompanyName(), "(주)에이티지"),
                    () -> assertEquals(jobInfo.getTitle(), "(주)에이티지(대전연구소) 서버 개발자 경력자 채용"),
                    () -> assertEquals(jobInfo.getIndustryCode(), "301"),
                    () -> assertEquals(jobInfo.getIndustryName(), "솔루션·SI·ERP·CRM"),
                    () -> assertEquals(jobInfo.getLocationCode(), "105000,105010,105020,105030,105040,118000"),
                    () -> assertEquals(jobInfo.getLocationName(), "대전 &gt; 대전전체,대전 &gt; 대덕구,대전 &gt; 동구,대전 &gt; 서구,대전 &gt; 유성구,세종 &gt; 세종특별자치시"),
                    () -> assertEquals(jobInfo.getJobTypeCode(), "1"),
                    () -> assertEquals(jobInfo.getJobTypeName(), "정규직"),
                    () -> assertEquals(jobInfo.getJobMidCode(), "2"),
                    () -> assertEquals(jobInfo.getJobMidName(), "IT개발·데이터"),
                    ()-> assertEquals(jobInfo.getJobCode(),"84,108,109,217,222,235,244,254,270,292,108,167,197,241,254,270,291"),
                    () -> assertEquals(jobInfo.getJobName(), "소프트웨어개발,솔루션업체,백엔드/서버개발,딥러닝,머신러닝,ElasticStack,Git,Java,Kubernetes,MongoDB,PostgreSQL,SpringBoot,S/W,Apache,Kafka,Spring"),
                    () -> assertEquals(jobInfo.getExperienceCode(), "2"),
                    () -> assertEquals(jobInfo.getExperienceMin(), 3),
                    () -> assertEquals(jobInfo.getExperienceMax(), 0),
                    () -> assertEquals(jobInfo.getExperienceName(), "경력3년↑"),
                    () -> assertEquals(jobInfo.getRequiredEducationCode(), "8"),
                    () -> assertEquals(jobInfo.getRequiredEducationName(), "대학교졸업(4년)이상"),
                    () -> assertEquals(jobInfo.getKeyword(), "소프트웨어개발,솔루션업체"),
                    () -> assertEquals(jobInfo.getSalaryCode(), "99"),
                    () -> assertEquals(jobInfo.getSalaryName(), "면접후 결정"),
                    () -> assertEquals(jobInfo.getPositionId(),48954525),
                    () -> assertEquals(jobInfo.getPostingDate(), TimeStampUtil.convertToLocalDateTime("1725438037")),
                    () -> assertEquals(jobInfo.getExpirationDate(), TimeStampUtil.convertToLocalDateTime("1988118000")),
                    () -> assertEquals(jobInfo.getCloseTypeCode(), "2"),
                    () -> assertEquals(jobInfo.getCloseTypeName(), "채용시")
            );
        }

        @Test
        void 학교정보데이터_에러(){

            assertThatThrownBy(()-> JobInfoJsonUtil.getTotalCount(error_json)).isInstanceOf(RestApiException.class);

        }
}