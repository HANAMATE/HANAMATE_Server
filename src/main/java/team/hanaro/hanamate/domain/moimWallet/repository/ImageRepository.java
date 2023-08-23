package team.hanaro.hanamate.domain.moimWallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.hanaro.hanamate.entities.Images;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Images,Long> {
    Optional<Images>findBySavedName(String fileName);
}
