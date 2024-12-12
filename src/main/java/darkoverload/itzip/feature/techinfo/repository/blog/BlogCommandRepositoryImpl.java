package darkoverload.itzip.feature.techinfo.repository.blog;

import darkoverload.itzip.feature.techinfo.domain.blog.Blog;
import darkoverload.itzip.feature.techinfo.model.entity.BlogEntity;
import darkoverload.itzip.feature.techinfo.service.blog.port.BlogCommandRepository;
import darkoverload.itzip.feature.techinfo.service.blog.port.BlogReadRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

/**
 * 블로그 명령(생성, 수정)을 처리하는 레포지토리 구현 클래스.
 */
@Repository
@RequiredArgsConstructor
public class BlogCommandRepositoryImpl implements BlogCommandRepository {

    private final JpaBlogCommandRepository repository;
    private final BlogReadRepository readRepository;

    /**
     * 새로운 블로그를 저장합니다.
     *
     * @param blog 저장할 블로그
     */
    @Override
    public Blog save(Blog blog) {
        return repository.save(BlogEntity.from(blog)).toModel();
    }

    /**
     * 블로그 소개글을 업데이트합니다.
     *
     * @param userId   사용자 ID
     * @param newIntro 새로운 소개글
     * @throws RestApiException 블로그 업데이트 실패 시 발생
     */
    @Override
    public Blog update(Long userId, String newIntro) {
        if (repository.update(userId, newIntro) < 0) {
            throw new RestApiException(CommonExceptionCode.UPDATE_FAIL_BLOG);
        }
        return readRepository.getByUserId(userId);
    }

    /**
     * 블로그의 공개 상태를 업데이트합니다.
     *
     * @param blogId 블로그 ID
     * @param status 새로운 공개 상태
     * @throws RestApiException 블로그 상태 업데이트 실패 시 발생
     */
    @Override
    public Blog update(Long blogId, boolean status) {
        try {
            if (repository.update(blogId, status) < 0) {
                throw new RestApiException(CommonExceptionCode.UPDATE_FAIL_BLOG);
            }
            return readRepository.getReferenceById(blogId);
        } catch (EntityNotFoundException e) {
            throw new RestApiException(CommonExceptionCode.NOT_FOUND_BLOG);
        }
    }

}
