package net.magdeev.poller.service;

import net.magdeev.poller.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findAuthorizedByUsername();

    Iterable<User> getAll();

    String update(Optional<User> user, String password, String confirmPassword);

    String registration(User user, String confirmPassword);

    String restore(String username, String secret);

    boolean isLogged();

    boolean isFirstInit();

    void initAdmin();

    String updateAvatar(Long id, MultipartFile file);
}
