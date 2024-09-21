package darkoverload.itzip.feature.techinfo.repository.blog.custom;

import darkoverload.itzip.feature.techinfo.model.entity.BlogEntity;

import java.util.Optional;

/**
 * BlogEntity에 대한 커스텀 데이터베이스 작업을 정의하는 인터페이스.
 * 블로그 ID나 사용자 ID를 기반으로 공개된 블로그를 조회하는 메서드를 포함함.
 */
public interface CustomBlogRepository {

    /**
     * 주어진 블로그 ID와 공개 여부를 기반으로 BlogEntity 조회.
     *
     * @param id 블로그 ID
     * @return 공개된 블로그의 Optional
     */
    Optional<BlogEntity> findByIdAndIsPublic(Long id);

    /**
     * 주어진 사용자 ID와 공개 여부를 기반으로 BlogEntity 조회.
     *
     * @param userId 사용자 ID
     * @return 공개된 블로그의 Optional
     */
    Optional<BlogEntity> findByUserIdAndIsPublic(Long userId);
}