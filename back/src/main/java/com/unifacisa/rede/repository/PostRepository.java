package com.unifacisa.rede.repository;

import com.unifacisa.rede.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    List<PostEntity> findByUserId(Long userId);

}
