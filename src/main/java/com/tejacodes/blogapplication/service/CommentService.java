package com.tejacodes.blogapplication.service;

import java.util.List;

import com.tejacodes.blogapplication.dto.CommentDTO;

public interface CommentService {

	public CommentDTO createComment(long postId, CommentDTO commentDTO);
	
	public List<CommentDTO> getAllCommentsByPostId(long postId);
	
	public CommentDTO getCommentByPostIdAndCommentId(long postId, long commentId);
	
	public CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO);
	
	public void deleteComment(long postId, long commentId);
}
