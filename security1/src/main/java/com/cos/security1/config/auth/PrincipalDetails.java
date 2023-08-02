package com.cos.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.security1.model.User;

//시큐리티가 "/login"을 낚아채서 로그인을 진행시킴
//로그인 진행이 완료가 되면 시큐리티 session을 만들어준다. (똑같은 공간을 쓰는 session인데 Security ContextHolder라는 곳에 세선 정보를 저장함?)
//시큐리티 session에 들어갈 수 있는 오브젝트가 정해져있다. (Authentication 타입 객체)
//Authentication 안에 User 정보가 있어야 됨
//User 오브젝트에 들어갈 수 있는 타입은 UserDetails 타입 객체

//Security Session =>(안에) Authentication 객체 =>(안에) UserDetails 객체(PrincipalDetails)

public class PrincipalDetails implements UserDetails{
	
	private User user; //콤포지션
	
	public PrincipalDetails(User user) {
		this.user = user;
	}

	//해당 User의 권한을 리턴하는 곳!!
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		return collect;
	}

	//패스워드를 리턴하는 곳!
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	//이름을 리턴하는 곳!
	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { //이 계정 너무 오래 쓴 거 아니니?
		return true;
	}

	@Override
	public boolean isEnabled() {
		//언제 false를 하느냐 
		//예: 우리 사이트 정책에서 로그인을 1년 이상 안 하면 휴면 계정으로 하기로 함
		//    >> 로그인 데이터를 UserRepository에 만들어서 (현재 시간 - 로그인 시간 = 1년 이상이면 false)
		return true;
	}

	
}
