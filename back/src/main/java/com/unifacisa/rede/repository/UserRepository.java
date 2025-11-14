package com.unifacisa.rede.repository;

import com.unifacisa.rede.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findByNameContainingIgnoreCaseOrUsernameContainingIgnoreCase(
            String name,
            String username
    );

}
