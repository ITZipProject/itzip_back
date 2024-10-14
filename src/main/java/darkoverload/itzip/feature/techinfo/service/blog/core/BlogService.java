package darkoverload.itzip.feature.techinfo.service.blog.core;

import darkoverload.itzip.feature.techinfo.service.blog.create.CreateBlogService;
import darkoverload.itzip.feature.techinfo.service.blog.find.FindBlogDetailsService;
import darkoverload.itzip.feature.techinfo.service.blog.find.FindBlogService;
import darkoverload.itzip.feature.techinfo.service.blog.find.FindBlogSummaryService;
import darkoverload.itzip.feature.techinfo.service.blog.update.UpdateBlogDisableService;
import darkoverload.itzip.feature.techinfo.service.blog.update.UpdateBlogIntroService;

/**
 * 블로그 관련 서비스 인터페이스를 집합한 인터페이스입니다.
 * 이 인터페이스는 블로그 생성, 수정, 조회에 필요한 메서드를 포함합니다.
 */
public interface BlogService extends
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
        FindBlogDetailsService {
}