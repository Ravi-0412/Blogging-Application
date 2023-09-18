package com.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	// 'Title' field ka naam h. means ye title filed me search karega post ko.
	// if you want to search on difffrent field you can make give function name according to that.
	// internally uses '%like' to find out the items from database.
	List<Post> findByTitleContaining(String title);
	
	// if we want to search post according to keywords in 'content'.
//	List<Post> findByContentContaining(String content);
	
	// custom functionm to get posts after searching using a field
	// meaning of writing query like this:
	// '%Key%': title me agar kahin bhi ye key dikhega usko fetch kar lega
	
//	@Query("select p from Post p where p.title like :key")
//	List<Post> searchByTitle(@Param("key") String title);
}
