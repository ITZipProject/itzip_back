package darkoverload.itzip.feature.techinfo.service.blog.find;

import darkoverload.itzip.feature.techinfo.controller.blog.response.BlogRecentPostsResponse;
import darkoverload.itzip.feature.techinfo.controller.post.response.PostBasicResponse;
import darkoverload.itzip.feature.techinfo.domain.Post;
import darkoverload.itzip.feature.techinfo.model.document.PostDocument;
import darkoverload.itzip.feature.techinfo.repository.post.PostRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FindBlogRecentPostsServiceImpl implements FindBlogRecentPostsService {

    // 한 번에 조회할 포스트 수 제한
    private static final int LIMIT = 4;

    // 블로그 정보 조회 서비스
    private final FindBlogService findBlogService;

    // 게시글 리포지토리
    private final PostRepository postRepository;

    public FindBlogRecentPostsServiceImpl(
            @Qualifier("findBlogServiceImpl") FindBlogService findBlogService,
            PostRepository postRepository
    ) {
        this.findBlogService = findBlogService; // 블로그 서비스 초기화
        this.postRepository = postRepository; // 게시글 리포지토리 초기화
    }

    @Override
    public BlogRecentPostsResponse findBlogRecentPostsByBlogIdAndCreateDate(Long blogId, LocalDateTime createDate) {
        // 블로그 ID를 사용하여 유저의 닉네임을 조회
        String nickname = findBlogService.findBlogById(blogId)
                .getUser()
                .getNickname();

        // 인접한 포스트들을 조회하고 PostBasicResponse 형태로 변환
        List<PostBasicResponse> postResponses = findRecentPostsByBlogIdAndCreateDate(blogId, createDate).stream()
                .map(Post::convertToPostBasicResponse)
                .toList();

        // 닉네임과 조회된 포스트를 포함한 응답 객체 반환
        return BlogRecentPostsResponse.builder()
                .nickname(nickname)
                .posts(postResponses)
                .build();
    }

    /**
     * 주어진 블로그 ID와 생성일을 기준으로 인접한 포스트 목록을 조회한다.
     *
     * @param blogId    조회할 블로그의 ID.
     * @param createDate 기준이 되는 게시글의 생성일.
     * @return 인접 포스트 목록.
     */
    private List<Post> findRecentPostsByBlogIdAndCreateDate(Long blogId, LocalDateTime createDate) {
        // 인접 포스트 조회 및 변환
        return postRepository.findAdjacentPosts(blogId, createDate, LIMIT).stream()
                .map(PostDocument::convertToDomainWithBasicFields)
                .toList();
    }
}