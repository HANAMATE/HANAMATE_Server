package team.hanaro.hanamate.domain.User;

public interface UsersRepository {'
    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);'
}
