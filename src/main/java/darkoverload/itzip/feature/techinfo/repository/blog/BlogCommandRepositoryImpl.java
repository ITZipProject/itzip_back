package darkoverload.itzip.feature.techinfo.repository.blog;

import darkoverload.itzip.feature.techinfo.domain.blog.Blog;
import darkoverload.itzip.feature.techinfo.model.entity.BlogEntity;
import darkoverload.itzip.feature.techinfo.service.blog.port.BlogCommandRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 블로그 명령(생성, 수정)을 처리하는 레포지토리 구현 클래스.
 */
@Repository
@RequiredArgsConstructor
public class BlogCommandRepositoryImpl implements BlogCommandRepository {

    private final JpaBlogCommandRepository repository;

    /**
     * 새로운 블로그를 저장합니다.
     *
     * @param blog 저장할 블로그
     */
    @Override
    public void save(Blog blog) {
        repository.save(BlogEntity.from(blog));
    }

    /**
     * 블로그 소개글을 업데이트합니다.
     *
     * @param userId   사용자 ID
     * @param newIntro 새로운 소개글
     * @throws RestApiException 블로그 업데이트 실패 시 발생
     */
    @Override
    public void update(Long userId, String newIntro) {
        if (repository.update(userId, newIntro) < 0) {
            throw new RestApiException(CommonExceptionCode.UPDATE_FAIL_BLOG);
        }
    }

    /**
     * 블로그의 공개 상태를 업데이트합니다.
     *
     * @param blogId 블로그 ID
     * @param status 새로운 공개 상태
     * @throws RestApiException 블로그 상태 업데이트 실패 시 발생
     */
    @Override
    public void update(Long blogId, boolean status) {
        if (repository.update(blogId, status) < 0) {
            throw new RestApiException(CommonExceptionCode.UPDATE_FAIL_BLOG);
        }
    }
}
