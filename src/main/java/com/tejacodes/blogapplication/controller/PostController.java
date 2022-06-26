package com.tejacodes.blogapplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tejacodes.blogapplication.dto.PostDTO;
import com.tejacodes.blogapplication.dto.PostResponse;
import com.tejacodes.blogapplication.service.PostService;
import com.tejacodes.blogapplication.util.AppConstants;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	/* 
	 * Constructor based Dependency Injection. 
	 * From Spring 4.3, if there is only one field inside spring Bean class, 
	 * @Autowired is not needed
	 */
	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	/*
	 * To create/add a new post
	 */
	@PostMapping
	public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO)
	{
		PostDTO resultPostDTO = postService.createPost(postDTO);
		return new ResponseEntity<>(resultPostDTO, HttpStatus.CREATED);
	}
	
	/*
	 * To get all the posts using Pagination and Sorting Features
	 */
	@GetMapping
	public PostResponse getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
			)
	{
		PostResponse postResponse = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
		return postResponse;
	}
	
	/*
	 * To get a post by it's id
	 */
	@GetMapping("/{id}")
	public PostDTO getPostById(@PathVariable("id") long id)
	{
		PostDTO postDTOResult = postService.getPostById(id);
		return postDTOResult;
	}
	
	/*
	 * To update a post
	 */
	@PutMapping("/{id}")
	public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO, @PathVariable("id") long id)
	{
		PostDTO postDTOResult = postService.updatePost(postDTO, id);
		return new ResponseEntity<>(postDTOResult,HttpStatus.OK);
	}
	
	/*
	 * To delete a post by id
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePostById(@PathVariable("id") long id)
	{
		postService.deletePostById(id);
		return ResponseEntity.ok(String.format("Post with id : %s is deleted successfully", id));
	}

}
