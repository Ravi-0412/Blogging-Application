package com.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// to show posts in form of pagination with all details

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {
	
	private List<PostDto> content;
	private int pageNumber;
	private int pageSize;
	private int totalPages;
	private long totalelements;
	private boolean lastPage; // is cur page is last page
}
