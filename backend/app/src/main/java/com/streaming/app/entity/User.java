package com.streaming.app.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // 스프링 부트에게 "이 클래스는 데이터베이스 테이블과 연결될 거야!"라고 알려줍니다.
@Table(name = "user_tb") // 실제 매핑될 데이터베이스의 테이블 이름을 지정합니다.
@Getter // 롬복(Lombok) 기능: 모든 필드의 Getter 메서드를 자동 생성해 줍니다.
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자를 안전하게 만들어 줍니다.
public class User {

    @Id // 기본키(PK)임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT (DB가 알아서 1씩 증가)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true) // 필수 값(NOT NULL), 중복 불가(UNIQUE)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String role; // 권한 등급: USER, STREAMER, ADMIN

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false; // 이메일 인증 여부 (기본값 false)

    @Column(name = "oauth_provider")
    private String oauthProvider; // 구글, 카카오 등 소셜 로그인 제공자

    @Column(name = "oauth_id")
    private String oauthId; // 소셜 로그인 고유 ID

    // 처음 유저를 생성할 때 값을 안전하게 넣기 위한 빌더(Builder) 패턴
    @Builder
    public User(String email, String password, String nickname, String role, String oauthProvider, String oauthId) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role != null ? role : "USER"; // 기본 권한은 USER
        this.oauthProvider = oauthProvider;
        this.oauthId = oauthId;
    }
}