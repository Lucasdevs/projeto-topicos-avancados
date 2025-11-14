package com.unifacisa.rede.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Column(length = 1000, name = "image")
    private String image;

    @Column(name = "creationdate")
    private LocalDateTime creationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
