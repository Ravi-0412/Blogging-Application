package com.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
