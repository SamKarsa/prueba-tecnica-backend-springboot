package com.samkarsa.prueba_backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "USER_TOKEN")
public class UserToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;

    @Column(nullable = false, unique = true, length = 255)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TokenType type;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = true)
    private Instant usedAt;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;
}
