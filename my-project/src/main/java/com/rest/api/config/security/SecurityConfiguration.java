package com.rest.api.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()  // rest api 이므로 기본설정 사용 안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트됨
            .csrf().disable()  // rest api 이므로 csrf 보안이 필요없으므로 disable
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // jwt token으로 인증 - 세션 필요없으므로 인증 안함
                .and()
                    .authorizeRequests()
                    .antMatchers("/*/signin", "/*/signup").permitAll()  // 가입 및 인증주소는 누구나 접근 가능
                    .antMatchers(HttpMethod.GET, "helloworld/**").permitAll()  // helloworld로 시작하는 GET 요청 리소스는 누구나 접근 가능
                .and()
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);  // jwt token 필터를 id/pw 인증 필터 전에 넣는다.
    }

    @Override  // ignore check swagger resource
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                    "/swagger-ui.html", "/webjars/**", "/swagger/**");
    }
}
