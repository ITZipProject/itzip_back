package darkoverload.itzip.feature.techinfo.domain.repository;

import darkoverload.itzip.feature.techinfo.domain.entity.Blog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BlogRepository {

    Blog save(Blog blog);

    Optional<Blog> findById(Long id);

    Optional<Blog> findByUserId(Long userId);

    Optional<Blog> findBlogByUser_Nickname(String nickname);

    @Query("SELECT b.id FROM Blog b WHERE b.user.nickname = :nickname")
    Optional<Long> findBlogIdByUserNickname(@Param("nickname") String nickname);

    List<Blog> findAllByIdIn(Set<Long> ids);

    void deleteById(Long id);

    void deleteAll();

}
