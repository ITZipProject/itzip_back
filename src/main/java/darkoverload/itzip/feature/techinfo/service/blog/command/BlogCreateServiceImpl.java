package darkoverload.itzip.feature.techinfo.service.blog.command;

import darkoverload.itzip.feature.techinfo.domain.Blog;
import darkoverload.itzip.feature.techinfo.repository.blog.BlogRepository;
import darkoverload.itzip.feature.user.domain.User;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogCreateServiceImpl implements BlogCreateService {

    // 블로그 정보를 저장하고 관리하는 리포지토리
    private final BlogRepository blogRepository;

    @Override
    public void createBlog(User user) {
        // 사용자 정보를 기반으로 블로그 객체 생성
        Blog blog = new Blog(user);

        // 블로그 객체를 엔티티로 변환 후 저장
        blogRepository.save(blog.convertToEntity());
    }
}