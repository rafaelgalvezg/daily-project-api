package dev.rafaelgalvezg.dailyprojectapi.service;

import dev.rafaelgalvezg.dailyprojectapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User save(User user);
    User update(Long id, User user);
    User findById(Long id);
    void delete(Long id);
    Page<User> findAll(Pageable pageable);
}
