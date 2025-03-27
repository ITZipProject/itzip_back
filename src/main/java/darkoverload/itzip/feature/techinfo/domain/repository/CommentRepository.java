package darkoverload.itzip.feature.techinfo.domain.repository;

import darkoverload.itzip.feature.techinfo.domain.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findById(Long id);

    Optional<Comment> findByUserId(Long userId);

    Optional<Comment> findByIdAndUser_Nickname(Long id, String nickname);

    Page<Comment> findAllByArticleId(String articleId, Pageable pageable);

    @Modifying
    @Query("UPDATE Comment c SET c.displayed = false WHERE c.articleId = :articleId")
    void setDisplayedFalseByArticleId(String articleId);

    void deleteById(Long id);

}
