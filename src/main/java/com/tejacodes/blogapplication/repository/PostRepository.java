package com.tejacodes.blogapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tejacodes.blogapplication.entity.Post;

/*
 *  No need of @Repository because the class that implements JpaRepository internally 
 *  is annotated with @Repository already (SimpleJPARepository)
 */
public interface PostRepository extends JpaRepository<Post, Long>{

}
