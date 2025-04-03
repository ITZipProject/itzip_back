package darkoverload.itzip.feature.techinfo.application.service.query.impl;

import darkoverload.itzip.feature.techinfo.ui.payload.response.BlogResponse;
import darkoverload.itzip.feature.techinfo.application.service.query.BlogQueryService;
import darkoverload.itzip.feature.techinfo.domain.entity.Blog;
import darkoverload.itzip.feature.techinfo.domain.repository.BlogRepository;
import darkoverload.itzip.global.config.response.code.CommonExceptionCode;
import darkoverload.itzip.global.config.response.exception.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class BlogQueryServiceImpl implements BlogQueryService {

    private final BlogRepository repository;

    public BlogQueryServiceImpl(final BlogRepository repository) {
        this.repository = repository;
    }

    @Override
    public BlogResponse getBlogResponseById(final Long id) {
        final Blog blog = repository.findById(id)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.BLOG_NOT_FOUND));
        return BlogResponse.from(blog);
    }

    @Override
    public BlogResponse getBlogResponseByUserNickname(final String nickname) {
        final Blog blog = repository.findBlogByUser_Nickname(nickname)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.BLOG_NOT_FOUND));
        return BlogResponse.from(blog);
    }

    @Override
    public Blog getBlogById(final Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.BLOG_NOT_FOUND));
    }


    @Override
    public Long getBlogIdByUserNickname(final String nickname) {
        log.info("블로그 닉네임 {}", nickname);
        return repository.findBlogIdByUserNickname(nickname)
                .orElseThrow(() -> new RestApiException(CommonExceptionCode.BLOG_NOT_FOUND));
    }

    @Override
    public Map<Long, Blog> getBlogMapByIds(final Set<Long> blogIds) {
        final List<Blog> blogs = repository.findAllByIdIn(blogIds);
        return blogs.stream()
                .collect(Collectors.toMap(Blog::getId, Function.identity()));
    }

}
