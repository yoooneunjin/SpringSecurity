package com.cos.security1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.security1.model.User;
import com.cos.security1.repository.UserRepository;

@Controller //"View를 리턴하겠다."는 뜻
public class IndexController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping({"","/"}) //주소를 두 개 넣음(공백과 슬래쉬)
	public String index() {
		//머스테치는 프로젝트 생성할 때 넣음
		//머스테치는 스프링에서 공식적으로 사용하라고 권장하는 템플릿 엔진임
		//머스테치의 기본 폴더는 src/main/resources/
		//뷰리졸버 설정: templates(prefix), .mustache(suffix)
		//근데 위에 있는 것은 안 넣어도 됨 >> 왜? 머스테치를 의존성 등록하면 기본적으로 위에 값이 잡힘 = 생략 가능	
		return "index"; //여기서 "index"는 View 얘는 src/main/resources/이 경로에서 index.html을 찾음
	}
	
	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}
	
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	
	@GetMapping("/loginForm") //이거는 스프링 시큐리티가 낚아챔!!! (=우리가 만든 것이 안 나옴) >>> SecurityConfig 파일 생성 후 작동 안 함
	public String loginForm() {
		return "loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}
	
	@PostMapping("/join")
	public String join(User user) {
		System.out.println(user);
		//여기서 중요한 것 권한은 여기서 강제로 부여한다. 
		user.setRole("ROLE_USER");
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword); //비밀번호 인코딩!
		
		user.setPassword(encPassword); //인코딩한 비밀번호를 다시 넣어서 DB에 저장할 거임!
		
		userRepository.save(user); 
		return "redirect:/loginForm";
	}
	
	@Secured("ROLE_ADMIN") //특정 메소드를 간단하게 막고 싶으면 이렇게 할 수 있음(?) seecurityConfig도 확인해라
	@GetMapping("/info")
	public @ResponseBody String info(){
		return "개인정보";
	}

	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") //여러 개 걸고 싶을 땡!
	@GetMapping("/data")
	public @ResponseBody String data(){
		return "데이터정보";
	}
	
	
}
