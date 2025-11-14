package com.unifacisa.rede.repository;

import com.unifacisa.rede.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
