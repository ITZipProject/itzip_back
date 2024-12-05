package darkoverload.itzip.feature.job.domain.job;

import darkoverload.itzip.feature.job.mock.JobInfoMockData;
import darkoverload.itzip.feature.job.util.TimeStampUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JobInfoAggregatorTest {

    public JobInfo jobInfoUpdateData = JobInfo.builder()
            .active("1")
            .closeTypeCode("1")
            .closeTypeCode("접수마감일")
            .companyHref("http://www.saramin.co.kr/zf_user/company-info/view?csn=2208134781&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api")
            .companyName("(주)이수시스템")
            .experienceCode("2")
            .experienceMin(5L)
            .experienceMax(10L)
            .experienceName("경력 5~10년")
            .expirationDate(TimeStampUtil.convertToLocalDateTime("1725448097"))
            .industryCode("301")
            .industryName("솔루션·SI·ERP·CRM")
            .jobCode("84,87,91,92,95,216,235,236,239,240,256,257,263,291,292,293,312,89,118,136,149,156,235,236,239,240,256,257,291,312")
            .jobMidCode("2")
            .jobMidName("IT개발·데이터")
            .jobName("소프트웨어개발,백엔드/서버개발,웹개발,퍼블리셔,프론트엔드,DBA,ECMAScript,Java,Javascript,jQuery,JSP,MyBatis,MySQL,OracleDB,Spring,SpringBoot,SQL,Vue.js,유지보수,솔루션,클라우드,ERP,IoT")
            .jobTypeCode("1")
            .jobTypeName("정규직")
            .keyword("소프트웨어개발")
            .locationCode("101000,101150")
            .locationName("서울 &gt; 서울전체,서울 &gt; 서초구")
            .positionId(48953038L)
            .expirationDate(TimeStampUtil.convertToLocalDateTime("1726757999"))
            .requiredEducationCode("8")
            .requiredEducationName("대학교졸업(4년)이상")
            .salaryCode("0")
            .salaryName("회사내규에 따름")
            .title("Cloud 인사시스템 개발자")
            .url("https://itzip.co.kr")
            .build();

    @Test
    void 정적_메서드_생성_테스트_코드() {

        JobInfoAggregator aggregator = JobInfoAggregator.create(new JobInfos(List.of(JobInfoMockData.jobInfoDataOne, JobInfoMockData.jobInfoDataSecond, JobInfoMockData.jobInfoDataThree)), new JobInfos(List.of(JobInfoMockData.jobInfoDataOne, JobInfoMockData.jobInfoDataSecond, JobInfoMockData.jobInfoDataThree)));

        assertThat(aggregator).isEqualTo(new JobInfoAggregator(new JobInfos(List.of(JobInfoMockData.jobInfoDataOne, JobInfoMockData.jobInfoDataSecond, JobInfoMockData.jobInfoDataThree)), new JobInfos(List.of(JobInfoMockData.jobInfoDataOne, JobInfoMockData.jobInfoDataSecond, JobInfoMockData.jobInfoDataThree))));
    }

    @Test
    void 디비_일급_컬렉션_EMPTY_메소드_TRUE_테스트() {
        JobInfoAggregator aggregator = JobInfoAggregator.create(new JobInfos(List.of(JobInfoMockData.jobInfoDataOne, JobInfoMockData.jobInfoDataSecond, JobInfoMockData.jobInfoDataThree)), new JobInfos(List.of()));

        assertThat(aggregator.isDbJobInfosEmpty()).isTrue();
    }

    @Test
    void 디비_일급_컬렉션_EMPTY_메소드_False_테스트() {
        JobInfoAggregator aggregator = JobInfoAggregator.create(new JobInfos(List.of(JobInfoMockData.jobInfoDataOne, JobInfoMockData.jobInfoDataSecond, JobInfoMockData.jobInfoDataThree)), new JobInfos(List.of(JobInfoMockData.jobInfoDataOne, JobInfoMockData.jobInfoDataSecond, JobInfoMockData.jobInfoDataThree)));

        assertThat(aggregator.isDbJobInfosEmpty()).isFalse();
    }

    @Test
    void 채용_공고_삭제_처리_리스트_테스트() {
        JobInfoAggregator aggregator = JobInfoAggregator.create(new JobInfos(List.of(JobInfoMockData.jobInfoDataSecond, JobInfoMockData.jobInfoDataThree)), new JobInfos(List.of(JobInfoMockData.jobInfoDataOne, JobInfoMockData.jobInfoDataSecond, JobInfoMockData.jobInfoDataThree)));

        assertThat(aggregator.makeDeleteJobInfoIds()).isEqualTo(new JobInfoIds(List.of(48953038L)));
    }

    @Test
    @DisplayName("API 데이터 기준으로 반환해야함")
    void 채용_공고_업데이트_처리_리스트_테스트() {
        JobInfoAggregator aggregator = JobInfoAggregator.create(new JobInfos(List.of(JobInfoMockData.jobInfoDataOne, JobInfoMockData.jobInfoDataSecond, JobInfoMockData.jobInfoDataThree)), new JobInfos(List.of(jobInfoUpdateData, JobInfoMockData.jobInfoDataSecond, JobInfoMockData.jobInfoDataThree)));

        assertThat(aggregator.makeUpdateJobInfos()).isEqualTo(new JobInfos(List.of(JobInfoMockData.jobInfoDataOne)));
    }

    @Test
    void 채용_저장_테스트() {
        JobInfoAggregator aggregator = JobInfoAggregator.create(new JobInfos(List.of(JobInfoMockData.jobInfoDataOne, JobInfoMockData.jobInfoDataSecond, JobInfoMockData.jobInfoDataThree)), new JobInfos(List.of(JobInfoMockData.jobInfoDataOne, JobInfoMockData.jobInfoDataSecond)));

        assertThat(aggregator.makeSaveJobInfos()).isEqualTo(new JobInfos(List.of(JobInfoMockData.jobInfoDataThree)));

    }

}