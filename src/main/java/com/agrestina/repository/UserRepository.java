package com.agrestina.repository;

import com.agrestina.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.active=true")
    Page<User> findActive(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.active=false")
    Page<User> findInactive(Pageable pageable);

    @Query("SELECT u FROM User u")
    Page<User> findAllUsers(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.login = :login")
    Optional<User> findUser(String login);

}

