package com.blog.service;

import java.util.List;

import com.blog.payloads.UserDto;

public interface UserService {
	
	// indirectly giving entity to service.
	// in this we can only give required details of entity not all detail.
	// or even we can give extra field not present in entity.
	UserDto createUser(UserDto user);
	
	
	// registering after spring security with all the details
	UserDto registerNewUser(UserDto user);
	
	// user ka detail + kis user ko update karna h.
	UserDto updateUser(UserDto user , Integer userId); 
	
	UserDto getUserById(Integer userId);
	
	List<UserDto>  getAllUsers();
	
	void deleteUser(Integer userId);
	

}
