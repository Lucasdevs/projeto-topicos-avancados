package com.unifacisa.rede.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "post", schema = "rede")
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(length = 500, nullable = false, name = "message")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
