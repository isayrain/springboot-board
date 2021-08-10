package com.rest.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder  // Builder 사용할 수 있게 함
@Entity   // JPA entity 임을 알림
@Getter   // getter 자동 생성
@NoArgsConstructor  // 인자 없는 생성자를 자동 생성
@AllArgsConstructor  // 인자 모두 갖춘 생성자를 자동 생성
@Table(name = "user")  // user 테이블과 매핑됨을 명시
public class User {
    @Id  // primary key 임을 알림
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // pk 생성 전략을 db에 위임
    private long msrl;
    @Column(nullable = false, unique = true, length = 30)  // uid 정의 : 필수, unique, 길이 30
    private String uid;
    @Column(nullable = false, length = 100)  // name 정의 : 필수, 길이 100
    private String name;
}
