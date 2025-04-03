package darkoverload.itzip.feature.techinfo.infrastructure.persistence;

import darkoverload.itzip.feature.techinfo.domain.entity.Article;
import darkoverload.itzip.feature.techinfo.domain.repository.ArticleRepository;
import darkoverload.itzip.feature.techinfo.infrastructure.persistence.custom.ArticleCustomRepository;
import darkoverload.itzip.global.config.querydsl.ExcludeFromJpaRepositories;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

@ExcludeFromJpaRepositories
public interface ArticleCrudRepository extends CrudRepository<Article, ObjectId>, ArticleRepository, ArticleCustomRepository {
}
