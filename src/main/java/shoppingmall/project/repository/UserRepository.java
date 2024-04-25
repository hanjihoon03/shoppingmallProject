package shoppingmall.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.User;
import shoppingmall.project.repository.custom.UserRepositoryCustom;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByLoginId(String loginId);
    boolean existsByLoginId(String loginId);
}
