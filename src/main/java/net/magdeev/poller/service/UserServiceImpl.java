package net.magdeev.poller.service;

import net.magdeev.poller.entity.Role;
import net.magdeev.poller.entity.User;
import net.magdeev.poller.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public String registration(User user, String confirmPassword) {
        if (userRepository.findUserByUsername(user.getUsername()) != null) {
            return "Пользователь с таким именем уже существует";
        }
        if (!confirmPassword.equals(user.getPassword())) {
            return "Введённые пароли не совпдают";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);
        return "success";
    }

    @Override
    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public boolean isLogged() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !(authentication.getName()).equals("anonymousUser");
    }

    @Override
    public void initAdmin() {
        List<Role> roles = Arrays.asList(Role.USER, Role.ADMIN);
        // TODO: change standard pass
        userRepository.save(new User("admin", passwordEncoder.encode("password"), "secret", new HashSet<>(roles)));
    }

    @Override
    public String restore(String username, String secret) {
        if (userRepository.findUserByUsername(username) == null) {
            return "Пользователя с таким логином не существует";
        }
        if (!userRepository.findUserByUsername(username).getSecret().equals(secret)) {
            return "Секретное слово введено неправильно";
        }
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int randomLimitedInt = 97 + (int)
                    (random.nextFloat() * (122 - 97 + 1));
            buffer.append((char) randomLimitedInt);
        }
        String newPassword = buffer.toString();
        userRepository.updatePassword(passwordEncoder.encode(newPassword), username);
        return "Новый пароль: " + newPassword;
    }

    @Override
    public Optional<User> findAuthorizedByUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName());
    }

    @Override
    public String update(Optional<User> user, String password, String confirmPassword) {
        if (!password.isBlank() && !confirmPassword.isBlank() && !password.equals(confirmPassword)) {
            return "Введённые пароли не совпдают";
        }
        userRepository.updatePassword(passwordEncoder.encode(password), user.get().getUsername());
        return "success";
    }

    public boolean isFirstInit() {
        return userRepository.findAll().size() == 0;
    }
}
