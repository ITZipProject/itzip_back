package darkoverload.itzip.feature.techinfo.service.blog.create;

import darkoverload.itzip.feature.user.domain.User;

/**
 * 블로그를 생성하는 서비스 인터페이스.
 * 이 인터페이스는 사용자가 블로그를 생성하는 메서드를 정의한다.
 */
public interface CreateBlogService {

    /**
     * 주어진 사용자를 기반으로 블로그를 생성한다.
     *
     * @param user 블로그를 생성할 사용자 정보.
     */
    void createBlog(User user);
}