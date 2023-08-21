package team.hanaro.hanamate.domain.moimWallet.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.Article;
import team.hanaro.hanamate.entities.Comment;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @EntityGraph(attributePaths = "comments")
    Optional<Article> findFetchById(Long id);

}
