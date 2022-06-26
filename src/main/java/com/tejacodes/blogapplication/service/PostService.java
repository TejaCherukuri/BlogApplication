package com.tejacodes.blogapplication.service;

import com.tejacodes.blogapplication.dto.PostDTO;
import com.tejacodes.blogapplication.dto.PostResponse;

public interface PostService {
	
	public PostDTO createPost(PostDTO postDTO);
	
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
	
	public PostDTO getPostById(long id);
	
	public PostDTO updatePost(PostDTO postDTO, long id);
	
	public void deletePostById(long id);
}
