package darkoverload.itzip.feature.job.mock;

import darkoverload.itzip.feature.job.domain.job.JobInfo;
import darkoverload.itzip.feature.job.util.TimeStampUtil;

public class JobInfoMockData {

    public static JobInfo jobInfoDataOne = JobInfo.builder()
            .companyName("(주)이수시스템")
            .experienceMin(5L)
            .experienceMax(10L)
            .experienceName("경력 5~10년")
            .expirationDate(TimeStampUtil.convertToLocalDateTime("1725448097"))
            .industryName("솔루션·SI·ERP·CRM")
            .jobName("소프트웨어개발,백엔드/서버개발,웹개발,퍼블리셔,프론트엔드,DBA,ECMAScript,Java,Javascript,jQuery,JSP,MyBatis,MySQL,OracleDB,Spring,SpringBoot,SQL,Vue.js,유지보수,솔루션,클라우드,ERP,IoT")
            .keyword("소프트웨어개발")
            .locationCode("101000,101150")
            .locationName("서울 &gt; 서울전체,서울 &gt; 서초구")
            .positionId(48953038L)
            .expirationDate(TimeStampUtil.convertToLocalDateTime("1726757999"))
            .title("Cloud 인사시스템 개발자")
            .url("http://www.saramin.co.kr/zf_user/jobs/relay/view?rec_idx=48953038&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api")
            .build();

    public static JobInfo jobInfoDataSecond = JobInfo.builder()
            .companyName("(주)넥스트테크놀로지")
            .experienceMin(0L)
            .experienceMax(0L)
            .experienceName("신입")
            .expirationDate(TimeStampUtil.convertToLocalDateTime("1725448097"))
            .industryName("정보보안·백신")
            .jobName("IDS·IPS,네트워크보안,방화벽,보안,정보보안,네트워크영업,솔루션기술영업,IT영업,H/W,S/W,영업")
            .keyword("IDS·IPS,네트워크보안,방화벽,보안,정보보안")
            .locationCode("101000")
            .locationName("서울 &gt; 서울전체")
            .positionId(48953031L)
            .expirationDate(TimeStampUtil.convertToLocalDateTime("1726757999"))
            .title("보안솔루션 및 네트워크장비 영업")
            .url("http://www.saramin.co.kr/zf_user/jobs/relay/view?rec_idx=48953031&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api")
            .build();

    public static JobInfo jobInfoDataThree = JobInfo.builder()
            .companyName("(주)다담서포트")
            .experienceMin(3L)
            .experienceMax(15L)
            .experienceName("경력 3~15년")
            .expirationDate(TimeStampUtil.convertToLocalDateTime("1725448097"))
            .industryName("쇼핑몰·오픈마켓")
            .jobName("쇼핑몰,오픈마켓,전자상거래,CSS,광고디자인,로고디자인,문구디자인,완구디자인,일러스트레이터,컨셉디자인,일러스트,HTML,PhotoShop,고객관리,Spring")
            .keyword("쇼핑몰,오픈마켓,전자상거래")
            .locationCode("101040")
            .locationName("서울 &gt; 강서구")
            .positionId(48952926L)
            .expirationDate(TimeStampUtil.convertToLocalDateTime("1726757999"))
            .title("[베스트냅킨] 일회용품 쇼핑몰 편집 디자이너 모집")
            .url("http://www.saramin.co.kr/zf_user/jobs/relay/view?rec_idx=48952926&utm_source=job-search-api&utm_medium=api&utm_campaign=saramin-job-search-api")
            .build();

}
