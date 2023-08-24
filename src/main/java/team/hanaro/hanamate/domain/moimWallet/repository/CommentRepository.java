package team.hanaro.hanamate.domain.moimWallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
