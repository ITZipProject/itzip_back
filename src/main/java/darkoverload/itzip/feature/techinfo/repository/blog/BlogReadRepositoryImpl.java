package darkoverload.itzip.feature.techinfo.repository.blog;

import darkoverload.itzip.feature.techinfo.domain.blog.Blog;
import darkoverload.itzip.feature.techinfo.model.entity.BlogEntity;
import darkoverload.itzip.feature.techinfo.service.blog.port.BlogReadRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 블로그 조회를 처리하는 레포지토리 구현 클래스.
 */
@Repository
@RequiredArgsConstructor
public class BlogReadRepositoryImpl implements BlogReadRepository {

    private final JpaBlogReadRepository repository;

    /**
     * 블로그 ID로 블로그를 조회합니다.
     *
     * @param id 블로그 ID
     * @return Optional<Blog>
     */
    @Override
    public Optional<Blog> findByBlogId(Long id) {
        return repository.findByBlogId(id).map(BlogEntity::toModel);
    }

    /**
     * 사용자 ID로 블로그를 조회합니다.
     *
     * @param id 사용자 ID
     * @return Optional<Blog>
     */
    @Override
    public Optional<Blog> findByUserId(Long id) {
        return repository.findByUserId(id).map(BlogEntity::toModel);
    }

    /**
     * 사용자 닉네임으로 블로그를 조회합니다.
     *
     * @param nickname 사용자 닉네임
     * @return Optional<Blog>
     */
    @Override
    public Optional<Blog> findByNickname(String nickname) {
        return repository.findByNickname(nickname).map(BlogEntity::toModel);
    }

    /**
     * 블로그 ID로 블로그를 조회하고, 없으면 예외를 발생시킵니다.
     *
     * @param id 블로그 ID
     * @return Blog
     * @throws RestApiException 블로그를 찾을 수 없을 때 발생
     */
    @Override
    public Blog getById(Long id) {
        return this.findByBlogId(id).orElseThrow(
                () -> new RestApiException(CommonExceptionCode.NOT_FOUND_BLOG)
        );
    }

    /**
     * 사용자 ID로 블로그를 조회하고, 없으면 예외를 발생시킵니다.
     *
     * @param id 사용자 ID
     * @return Blog
     * @throws RestApiException 블로그를 찾을 수 없을 때 발생
     */
    @Override
    public Blog getByUserId(Long id) {
        return this.findByUserId(id).orElseThrow(
                () -> new RestApiException(CommonExceptionCode.NOT_FOUND_BLOG)
        );
    }

    /**
     * 사용자 닉네임으로 블로그를 조회하고, 없으면 예외를 발생시킵니다.
     *
     * @param nickname 사용자 닉네임
     * @return Blog
     * @throws RestApiException 블로그를 찾을 수 없을 때 발생
     */
    @Override
    public Blog getByNickname(String nickname) {
        return this.findByNickname(nickname).orElseThrow(
                () -> new RestApiException(CommonExceptionCode.NOT_FOUND_BLOG)
        );
    }

    /**
     * 블로그 ID로 블로그의 프록시 객체를 조회합니다.
     *
     * @param id 블로그 ID
     * @return Blog
     */
    @Override
    public Blog getReferenceById(Long id) {
        return repository.getReferenceById(id).toModel();
    }

}
