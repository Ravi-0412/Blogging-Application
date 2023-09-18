package com.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.blog.entities.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private Integer postId;
	
	private String title;

	private String content;

	private String imageName;

	private Date addedDate;
	
	// taking 'dto' for category and 'user' to avoid infinite loop
		// 'dto' me post nhi h but 'user' and 'category' me h .

	private CategoryDto category; // post kis category ka h

	private UserDto user; // post kis user ka h
	
	// to get all comments. using 'dto' to avoid infinite loop.
	private Set<CommentDto> comments = new HashSet<>();  
	

}
