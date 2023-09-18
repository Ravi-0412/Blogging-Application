package com.blog.service.impl;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.UserDto;
import com.blog.repositories.RoleRepo;
import com.blog.repositories.UserRepo;
import com.blog.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		// save karte samay user pass karenge but parameter me 'dto' ka object pass kar rhe
		// isliye phle hmko convert karna hoga dto to user.
		
		User user= this.dtoToUser(userDto);
		
		User savedUser = this.userRepo.save(user);
		
		return this.userToDto(savedUser);   // return karte samay phir change kar denge
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		// first get the user
		
		// user h tb us user ka detail aa jayega nhi to exception(custom) throw kar dega(using lambda function).
		// exception me 3 para: kon sa resource nhi mil rha, kon sa field nhi mil rha , field ka value
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id",userId));
		
		// now update this 'user' with provided detail i.e 'userDto'.
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		
		// update karne ke bad user ko db me save kar do.
		User updatedUser = this.userRepo.save(user);
		
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id",userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		// hmko har ek user ko 'userDto' me convert karna hoga and usko return karna hoga list me.
		
		List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		// 1st get the user
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User", "id",userId));
		System.out.println("deleting user.....");
		// then delete the user
		this.userRepo.delete(user);
	}
	
//	// to convert userdto to user manually
//	
//	public User dtoToUser(UserDto userDto)
//	{
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
//		return user;
//	}
//	
	// to convert userdto to user using modelMapper
	
	public User dtoToUser(UserDto userDto)
	{	
		// (kis object ko convert karna h , kis class ke object me convert karna h)
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}
	
	
	// to convert userdto to user using modelMapper
	
	public UserDto userToDto(User user)
	{
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	
	}
	// registering(creating) new user after spring security 
	// with 'roles' and encoded password.
	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		
		// encode password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		// setting the roles to user
		// nya user ko phle admin bnayenge
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User newUser = this.userRepo.save(user);
		
		return this.modelMapper.map(newUser, UserDto.class);
	}
		
		
		
		
	
	
	
	
	
	
	
	
	

}
