package darkoverload.itzip.feature.techinfo.service.blog.facade;

import darkoverload.itzip.feature.techinfo.service.blog.command.BlogCreateService;
import darkoverload.itzip.feature.techinfo.service.blog.query.BlogReadDetailsService;
import darkoverload.itzip.feature.techinfo.service.blog.query.BlogReadRecentPostsService;
import darkoverload.itzip.feature.techinfo.service.blog.query.BlogReadService;
import darkoverload.itzip.feature.techinfo.service.blog.query.BlogReadSummaryService;
import darkoverload.itzip.feature.techinfo.service.blog.command.BlogDisableService;
import darkoverload.itzip.feature.techinfo.service.blog.command.BlogUpdateIntroService;

/**
 * 블로그 관련 서비스들을 통합하는 퍼사드 인터페이스입니다.
 * 블로그 생성, 수정, 조회 등 다양한 기능을 제공합니다.
 */
public interface BlogFacade extends
        // 블로그 생성 서비스
        BlogCreateService,

        // 블로그 소개 수정 서비스
        BlogUpdateIntroService,

        // 블로그 비활성화 서비스
        BlogDisableService,

        // 블로그 조회 서비스
        BlogReadService,

        // 블로그 요약 조회 서비스
        BlogReadSummaryService,

        // 블로그 세부 정보 조회 서비스
        BlogReadDetailsService,

        // 블로그의 최근 포스트 조회 서비스
        BlogReadRecentPostsService {
}