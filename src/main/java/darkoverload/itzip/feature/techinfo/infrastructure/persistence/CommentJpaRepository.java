package darkoverload.itzip.feature.techinfo.infrastructure.persistence;

import darkoverload.itzip.feature.techinfo.domain.entity.Comment;
import darkoverload.itzip.feature.techinfo.domain.repository.CommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long>, CommentRepository {
}
