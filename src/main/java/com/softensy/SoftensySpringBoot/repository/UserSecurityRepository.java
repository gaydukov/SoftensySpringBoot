package com.softensy.SoftensySpringBoot.repository;

import com.softensy.SoftensySpringBoot.entity.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSecurityRepository extends JpaRepository<UserSecurity, Long> {

    Optional<UserSecurity> findByLogin(String login);

}
