package com.tejacodes.blogapplication.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.tejacodes.blogapplication.dto.PostDTOv2;
import com.tejacodes.blogapplication.dto.PostResponse;
import com.tejacodes.blogapplication.service.PostService;
import com.tejacodes.blogapplication.util.AppConstants;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	/* 
	 * Constructor based Dependency Injection. 
	 * From Spring 4.3, if there is only one constructor inside spring Bean class, 
	 * @Autowired is not needed
	 */
	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	/*
	 * To create/add a new post
	 * Only Admin is allowed to create a post - Secured using @PreAuthroize of Spring Security
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO)
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
	 * To get a post by it's id (Version 1)
	 */
	@GetMapping("/v1/{id}")	// One way to version your Rest API - Versioning through URI
	//@GetMapping(value = "/{id}", params = "version=1") // Versioning through Query Parameters - /api/posts/1?version=1
	//@GetMapping(value = "/{id}", headers = "X-API-VERSION=1") // Versioning through Custom Headers - /api/posts/1 (Need to pass custom header)
	//@GetMapping(value = "/{id}", produces = "application/vdn.tejacodes-v1+json") // Version through content negotiation {Need to pass this in Accept Header of HttpRequest}
	public PostDTO getPostByIdv1(@PathVariable("id") long id)
	{
		PostDTO postDTOResult = postService.getPostById(id);
		return postDTOResult;
	}
	
	/*
	 * To get a post by it's id (Version 2)
	 */
	@GetMapping("/v2/{id}")	// One way to version your Rest API - Versioning through URI
	//@GetMapping(value = "/{id}", params = "version=2") // Versioning through Query Parameters - /api/posts/1?version=2
	//@GetMapping(value = "/{id}", headers = "X-API-VERSION=2") // Versioning through Custom Headers - /api/posts/1 (Need to pass custom header)
	//@GetMapping(value = "/{id}", produces = "application/vdn.tejacodes-v2+json") // Version through content negotiation {Need to pass this in Accept Header of HttpRequest}
	public PostDTOv2 getPostByIdv2(@PathVariable("id") long id)
	{
		PostDTO postDTOResult = postService.getPostById(id);
		
		PostDTOv2 postDTOv2 = new PostDTOv2();
		postDTOv2.setId(postDTOResult.getId());
		postDTOv2.setTitle(postDTOResult.getTitle());
		postDTOv2.setContent(postDTOResult.getContent());
		postDTOv2.setDescription(postDTOResult.getDescription());
		postDTOv2.setComments(postDTOResult.getComments());
		
		ArrayList<String> tags = new ArrayList<>();
		tags.add("Java");
		tags.add("Spring");
		tags.add("Spring Boot");
		
		postDTOv2.setTags(tags);
		
		return postDTOv2;
	}
	
	
	/*
	 * To update a post
	 * Only Admin is allowed to update a post - Secured using @PreAuthroize of Spring Security
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable("id") long id)
	{
		PostDTO postDTOResult = postService.updatePost(postDTO, id);
		return new ResponseEntity<>(postDTOResult,HttpStatus.OK);
	}
	
	/*
	 * To delete a post by id
	 * Only Admin is allowed to delete a post - Secured using @PreAuthroize of Spring Security
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePostById(@PathVariable("id") long id)
	{
		postService.deletePostById(id);
		return ResponseEntity.ok(String.format("Post with id : %s is deleted successfully", id));
	}

}
