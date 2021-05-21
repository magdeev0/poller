package net.magdeev.poller.repository;

import net.magdeev.poller.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query
    User findUserByUsername(String username);

    @Query
    Optional<User> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("update User u set u.password = :password where u.username = :username")
    void updatePassword(@Param("password") String password,
                        @Param("username") String username);

    @Modifying
    @Transactional
    @Query("update User u set u.file = :file where u.id = :id")
    void updateAvatar(@Param("file") byte[] file,
                      @Param("id") Long id);
}
