package darkoverload.itzip.feature.techinfo.service.blog.query;

import darkoverload.itzip.feature.techinfo.domain.Blog;

/**
 * 블로그 조회 서비스를 정의하는 인터페이스.
 * 이 인터페이스는 블로그 ID를 기반으로 블로그를 조회하는 메서드를 제공한다.
 */
public interface BlogReadService {

    /**
     * 블로그 ID를 사용하여 블로그를 조회한다.
     *
     * @param id 조회할 블로그의 ID.
     * @return 해당 ID에 해당하는 블로그 객체.
     */
    Blog getBlogById(Long id);
}