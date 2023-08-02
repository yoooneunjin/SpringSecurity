package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.model.User;

//CRUD 함수를 JpaRepository가 들고 있음
// @Repository가 없어도 IoC가 된다. 왜? JpaRepository를 상속했기 때문에!
public interface UserRepository extends JpaRepository<User, Integer>{
	//findBy 까지는 규칙 -> Username 문법
	//select * from user where username = ?
	public User findByUsername(String username); //이게 궁금하다면 JPA 쿼리 메소드를 검색!!
	
}
