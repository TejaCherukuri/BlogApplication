package com.tejacodes.blogapplication.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tejacodes.blogapplication.dto.PostDTO;
import com.tejacodes.blogapplication.dto.PostResponse;
import com.tejacodes.blogapplication.entity.Post;
import com.tejacodes.blogapplication.exception.ResourceNotFoundException;
import com.tejacodes.blogapplication.repository.PostRepository;
import com.tejacodes.blogapplication.service.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	/* 
	 * Constructor based dependency injection. 
	 * From Spring 4.3, if there is only one field inside spring Bean class, 
	 * @Autowired is not needed
	 */
	private PostRepository postRepository;
	private ModelMapper mapper;
	
	public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	/*
	 * Service to store/create the post into Database
	 */
	@Override
	public PostDTO createPost(PostDTO postDTO) {
		
		//convert DTO to Entity
		Post post = mapToEntity(postDTO);
		
		//Saving to DB
		Post newPost = postRepository.save(post);
		
		//convert Entity to DTO
		PostDTO newPostDTO = mapToDTO(newPost);
		
		return newPostDTO;
	}
	
	
	/*
	 * Method to get all posts using Pagination and Sorting 
	 * (This support is provided by PaginationAndSorting Interface which is extended by JpaRepository)
	 */
	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
		
		// Creating sort class based on direction passed dynamically
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy) : Sort.by(sortBy).descending();
		
		//Creating Pageable object with sorting
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		//findAll(Pageable) is a method of interface PaginationAndSorting
		Page<Post> page = postRepository.findAll(pageable);
		
		List<Post> posts = page.getContent();
		
		List<PostDTO> postDTOList = posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(postDTOList);
		postResponse.setPageNo(page.getNumber());
		postResponse.setPageSize(page.getSize());
		postResponse.setTotalElements(page.getTotalElements());
		postResponse.setTotalPages(page.getTotalPages());
		postResponse.setLastPage(page.isLast());
		
		return postResponse;
	}

	/*
	 * Method to get a Post by Id
	 */
	@Override
	public PostDTO getPostById(long id) {
		
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		PostDTO postDTOResult = mapToDTO(post);
		
		return postDTOResult;
	}

	/*
	 * Method to update a Post
	 */
	@Override
	public PostDTO updatePost(PostDTO postDTO, long id) {
		
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		
		post.setTitle(postDTO.getTitle());
		post.setDescription(postDTO.getDescription());
		post.setContent(postDTO.getContent());
		postRepository.save(post);
		
		PostDTO postDTOResult = mapToDTO(post);
		return postDTOResult;
	}

	/*
	 * To delete a post by id
	 */
	@Override
	public void deletePostById(long id) {
		
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		postRepository.delete(post);
		
	}
	
	/*
	 * Method to convert Entity to DTO
	 */
	private PostDTO mapToDTO(Post post) {
		
//		PostDTO postDTO = new PostDTO();
//		
//		postDTO.setId(post.getId());
//		postDTO.setTitle(post.getTitle());
//		postDTO.setDescription(post.getDescription());
//		postDTO.setContent(post.getContent());
		
		PostDTO postDTO = mapper.map(post, PostDTO.class);
		
		return postDTO;
	}

	/*
	 * Method to convert DTO to Entity
	 */
	private Post mapToEntity(PostDTO postDTO)
	{
//		Post post = new Post();
//		
//		post.setTitle(postDTO.getTitle());
//		post.setDescription(postDTO.getDescription());
//		post.setContent(postDTO.getContent());
		
		Post post = mapper.map(postDTO, Post.class);
		
		return post;
	}


}
