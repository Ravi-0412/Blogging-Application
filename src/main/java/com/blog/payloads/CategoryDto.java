package com.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotEmpty 
	@Size(min = 3, message ="category title must be of at least 3 char !!")
	private String categoryTitle;
	
	@NotEmpty 
	@Size(min = 6, message ="category description must be of at least 6 char !!")
	private String categoryDescription;

}
