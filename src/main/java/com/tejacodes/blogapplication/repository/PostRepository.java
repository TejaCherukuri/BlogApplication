package com.tejacodes.blogapplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tejacodes.blogapplication.entity.Post;

/*
 *  No need of @Repository because the class that implements JpaRepository internally 
 *  is annotated with @Repository already (SimpleJPARepository)
 */
public interface PostRepository extends JpaRepository<Post, Long>{
	
	// JPQL
	@Query("SELECT p FROM Post p WHERE p.title LIKE CONCAT('%',:query,'%')"
			+ "OR p.description LIKE CONCAT('%',:query,'%')")
	List<Post> searchPosts(String query);
	
	//SQL Native Query
	@Query(value = "SELECT * FROM posts WHERE p.title LIKE CONCAT('%',:query,'%')"
			+ "OR p.description LIKE CONCAT('%',:query,'%')", nativeQuery = true)
	List<Post> searchPostsSQL(String query);
}
