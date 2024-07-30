package darkoverload.itzip.user.repository;

import darkoverload.itzip.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    Optional<UserEntity> findByNickname(String nickname);
}
