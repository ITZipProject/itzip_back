package darkoverload.itzip.feature.techinfo.repository.blog;

import darkoverload.itzip.feature.techinfo.model.entity.BlogEntity;
import darkoverload.itzip.feature.techinfo.repository.blog.custom.CustomBlogRepository;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Blog 관련 데이터베이스 접근을 위한 리포지토리.
 * 기본적인 JpaRepository 기능과 커스텀 메서드를 포함함.
 */
public interface BlogRepository extends JpaRepository<BlogEntity, Long>, CustomBlogRepository {
}