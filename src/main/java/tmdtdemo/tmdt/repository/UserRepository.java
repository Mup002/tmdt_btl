package tmdtdemo.tmdt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tmdtdemo.tmdt.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByUsername(String username);
    User findUserById(Long id);
    @Query(value = "SELECT u.* FROM users u JOIN orderdetails o ON u.user_id = o.user_id " +
            "GROUP BY u.user_id ORDER BY SUM(o.total) DESC LIMIT 3", nativeQuery = true)
    List<User> findTop3UsersByTotalOrder();
}
