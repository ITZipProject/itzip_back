package darkoverload.itzip.feature.techinfo.service.blog.command;

/**
 * 블로그 비활성화 서비스를 정의하는 인터페이스.
 * 이 인터페이스는 특정 블로그를 비활성화하는 메서드를 제공한다.
 */
public interface BlogDisableService {

    /**
     * 지정된 블로그를 비활성화한다.
     *
     * @param blogId 비활성화할 블로그의 ID.
     */
    void disableBlog(Long blogId);
}