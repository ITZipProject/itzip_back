package darkoverload.itzip.feature.techinfo.service.blog;

import darkoverload.itzip.feature.techinfo.service.blog.create.CreateBlogService;
import darkoverload.itzip.feature.techinfo.service.blog.find.FindBlogDetailsService;
import darkoverload.itzip.feature.techinfo.service.blog.find.FindBlogRecentPostsService;
import darkoverload.itzip.feature.techinfo.service.blog.find.FindBlogService;
import darkoverload.itzip.feature.techinfo.service.blog.find.FindBlogSummaryService;
import darkoverload.itzip.feature.techinfo.service.blog.update.UpdateBlogDisableService;
import darkoverload.itzip.feature.techinfo.service.blog.update.UpdateBlogIntroService;

/**
 * 블로그 관련 서비스들을 통합하는 퍼사드 인터페이스입니다.
 * 블로그 생성, 수정, 조회 등 다양한 기능을 제공합니다.
 */
public interface BlogFacade extends
        // 블로그 생성 서비스
        CreateBlogService,

        // 블로그 소개 수정 서비스
        UpdateBlogIntroService,

        // 블로그 비활성화 서비스
        UpdateBlogDisableService,

        // 블로그 조회 서비스
        FindBlogService,

        // 블로그 요약 조회 서비스
        FindBlogSummaryService,

        // 블로그 세부 정보 조회 서비스
        FindBlogDetailsService,

        // 블로그의 최근 포스트 조회 서비스
        FindBlogRecentPostsService {
}