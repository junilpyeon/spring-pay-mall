package com.jpabook.jpashop.config.security;

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
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable() // rest api �̹Ƿ� �⺻���� ������. �⺻������ �������� �α����� ȭ������ �����̷�Ʈ �ȴ�.
            .csrf().disable() // rest api�̹Ƿ� csrf ������ �ʿ�����Ƿ� disableó��.
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token���� �����Ұ��̹Ƿ� �����ʿ�����Ƿ� ��������.
            .and()
                .authorizeRequests() // ���� ������Ʈ�� ���� ������ üũ
                    .antMatchers("/*/signin", "/*/signin/**", "/*/signup", "/*/signup/**", "/social/**").permitAll() // ���� �� ���� �ּҴ� ������ ���ٰ���
                    .antMatchers(HttpMethod.GET, "/exception/**", "/helloworld/**","/actuator/health", "/v1/board/**", "/favicon.ico").permitAll() // ��ϵ� GET��û ���ҽ��� ������ ���ٰ���
                    .anyRequest().hasRole("USER") // �׿� ������ ��û�� ��� ������ ȸ���� ���� ����
            .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
            .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // jwt token ���͸� id/password ���� ���� ���� �־��.

    }

    @Override // ignore swagger security config
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");

    }
}
