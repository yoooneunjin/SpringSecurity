package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터 체인에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true) //securedEnabled=true => secured 어노테이션 활성화, prePostEnabled=true => preAuthorize 어노테이션 활성화 
public class SecurityConfig{
	
	//해당 메소드에 리턴되는 오브젝트를 IoC로 등록해준다!
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.csrf(AbstractHttpConfigurer::disable); 이게 좋을 것 같은데 이해를 못해서 아직 뭔지 모름ㅎ,,,
        
        http.csrf().disable();
        http.authorizeHttpRequests()
                .requestMatchers("/user/**").authenticated() //로그인한 사람만 들어올 수 있다
                .requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN") //로그인 + 매니저, 관리자 권한이 있으면 들어올 수 있다. 
                .requestMatchers("/admin/**").hasRole("ADMIN") //로그인 + 관리자 권한이 있으면 들어올 수 있다. 
                .anyRequest().permitAll() //위에 3개 주소가 아니면 다 들어갈 수 있다. 
                .and().formLogin().loginPage("/loginForm") //권한 없는 상태에서 페이지 들어갈 때 login 페이지로 이동시킴
                .loginProcessingUrl("/login") //login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다. 
                .defaultSuccessUrl("/");
                
        return http.build();
    }

    /*
    기존: WebSecurityConfigurerAdapter를 상속하고 configure매소드를 오버라이딩하여 설정하는 방법
    => 현재: SecurityFilterChain을 리턴하는 메소드를 빈에 등록하는 방식(컴포넌트 방식으로 컨테이너가 관리)
    //https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter

    @Override
    protected void configure(HttpSecurity http) throws  Exception{
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin").access("\"hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();
    }

     */
}
