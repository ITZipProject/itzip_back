package darkoverload.itzip.feature.techinfo.service.blog.find;

import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogRecentPostsResponse;

import java.time.LocalDateTime;

/**
 * 블로그의 최근 포스트를 조회하는 서비스를 정의하는 인터페이스.
 * 이 인터페이스는 주어진 블로그 ID와 생성일을 기반으로 최근 포스트 목록을 조회하는 메서드를 제공한다.
 */
public interface FindBlogRecentPostsService {

    /**
     * 블로그 ID와 생성일을 사용하여 해당 블로그의 최근 포스트 목록을 조회한다.
     * 블로그 ID를 통해 블로그 정보를 찾고, 생성일을 기준으로 인접한 포스트를 반환한다.
     *
     * @param blogId    조회할 블로그의 ID.
     * @param createDate 기준이 되는 포스트의 생성일.
     * @return 조회된 블로그의 최근 포스트 목록 응답.
     */
    BlogRecentPostsResponse findBlogRecentPostsByBlogIdAndCreateDate(
            Long blogId,
            LocalDateTime createDate
    );
}