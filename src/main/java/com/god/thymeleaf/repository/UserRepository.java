package com.god.thymeleaf.repository;

import com.god.thymeleaf.model.Board;
import com.god.thymeleaf.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"boards"})
    List<User> findAll();

    User findByUsername(String username);
}

