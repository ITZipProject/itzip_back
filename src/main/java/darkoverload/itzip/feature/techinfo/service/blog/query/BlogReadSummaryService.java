package darkoverload.itzip.feature.techinfo.service.blog.query;

import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogSummaryResponse;

/**
 * 블로그 요약 정보를 조회하는 서비스를 정의하는 인터페이스.
 * 이 인터페이스는 블로그 ID를 기반으로 블로그 요약 정보를 조회하는 메서드를 제공한다.
 */
public interface BlogReadSummaryService {

    /**
     * 지정된 블로그 ID에 대한 요약 정보를 조회한다.
     *
     * @param blogId 조회할 블로그의 ID.
     * @return 해당 블로그의 요약 정보를 포함하는 BlogSummaryResponse 객체.
     */
    BlogSummaryResponse getBlogSummaryById(Long blogId);
}