package darkoverload.itzip.feature.techinfo.service.blog.find;

import darkoverload.itzip.feature.techinfo.domain.Blog;
import darkoverload.itzip.feature.techinfo.model.entity.BlogEntity;
import darkoverload.itzip.feature.techinfo.repository.blog.BlogRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindBlogServiceImpl implements FindBlogService {

    // 블로그 정보를 저장하고 관리하는 리포지토리
    private final BlogRepository blogRepository;

    @Override
    @Transactional(readOnly = true)
    public Blog findBlogById(Long id) {
        // 블로그 ID와 공개 상태를 기준으로 블로그를 조회
        return blogRepository.findByIdAndIsPublic(id)
                .map(BlogEntity::convertToDomain) // 엔티티를 도메인 객체로 변환
                .orElseThrow(
                        () -> new RestApiException(CommonExceptionCode.NOT_FOUND_BLOG) // 블로그를 찾을 수 없을 때 예외 발생
                );
    }
}