package shoppingmall.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shoppingmall.project.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
