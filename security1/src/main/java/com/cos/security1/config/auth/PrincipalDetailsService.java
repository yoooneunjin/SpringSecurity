package com.cos.security1.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

//시큐리티 설정에서 loginProcessingUrl("/login");
//login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어 있는 loadUserByUsername 함수가 실행됨(loadUserByUsername 타입은 꼭 맞춰야 함)
@Service
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	
	//이거 리턴은 Authentication 여기로 들어감
	//Security Session => Authentication => UserDetails 이니까ㅣ!!
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//loadUserByUsername 여기서 받는 username은 View에서 받은 input name이랑 같아야 함
		//위에 있는 말이 아니라 기본적으로 username이어야 한다는 것 같은데,,?
		
		User userEntity	= userRepository.findByUsername(username);
		if(userEntity != null) {
			return new PrincipalDetails(userEntity);
		}
		return null;
	}

	
}
