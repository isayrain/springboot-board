package com.rest.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder  // Builder 사용할 수 있게 함
@Entity   // JPA entity 임을 알림
@Getter   // getter 자동 생성
@NoArgsConstructor  // 인자 없는 생성자를 자동 생성
@AllArgsConstructor  // 인자 모두 갖춘 생성자를 자동 생성
@Table(name = "user")  // user 테이블과 매핑됨을 명시
public class User implements UserDetails {
    @Id  // primary key 임을 알림
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // pk 생성 전략을 db에 위임
    private long msrl;
    @Column(nullable = false, unique = true, length = 30)  // uid 정의 : 필수, unique, 길이 30
    private String uid;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, length = 100)  // password 정의 : 필수, 길이 100
    private String password;
    @Column(nullable = false, length = 100)  // name 정의 : 필수, 길이 100
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.uid;
    }

    // 아래는 Security에서 사용하는 회원 상태값
    // 여기서는 사용하지 않음(true로 지정)
    // Json 결과로 출력하지 않을 데이터는 WRITE_ONLY 어노테이션 선언
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {  // 계정이 만료되지 않았는지
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {  // 계정이 잠기지 않았는지
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {  // 계정 패스워드가 만료되지 않았는지
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {  // 계정이 사용 가능한지
        return true;
    }
}
