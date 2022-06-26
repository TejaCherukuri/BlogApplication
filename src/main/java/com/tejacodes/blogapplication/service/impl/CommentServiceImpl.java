package com.tejacodes.blogapplication.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tejacodes.blogapplication.dto.CommentDTO;
import com.tejacodes.blogapplication.entity.Comment;
import com.tejacodes.blogapplication.entity.Post;
import com.tejacodes.blogapplication.exception.BlogApiException;
import com.tejacodes.blogapplication.exception.ResourceNotFoundException;
import com.tejacodes.blogapplication.repository.CommentRepository;
import com.tejacodes.blogapplication.repository.PostRepository;
import com.tejacodes.blogapplication.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	/*
	 * Constructor based Dependency Injection
	 */
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
	}


	@Override
	public CommentDTO createComment(long postId, CommentDTO commentDTO) {
		
		Comment comment = mapToEntity(commentDTO);
		Post post = postRepository.findById(postId)
								  .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		//Setting the post to comment
		comment.setPost(post);
		
		//Save the comment
		Comment commentResult = commentRepository.save(comment);
		CommentDTO commentDTOResult = mapToDTO(commentResult);
		
		return commentDTOResult;
	}


	@Override
	public List<CommentDTO> getAllCommentsByPostId(long postId) {
		
		List<Comment> comments = commentRepository.findByPostId(postId);
												  
		List<CommentDTO> commentDTOList = comments.stream().map((comment) -> mapToDTO(comment)).collect(Collectors.toList());
		
		return commentDTOList;
	}


	@Override
	public CommentDTO getCommentByPostIdAndCommentId(long postId, long commentId) {
		
		Post post = postRepository.findById(postId)
				  .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment" ,"id", commentId));
		
		if(comment.getPost().getId() != post.getId())
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Post have no comments with the given commentId");
		
		CommentDTO commentDToResult = mapToDTO(comment);
		
		return commentDToResult;
	}


	@Override
	public CommentDTO updateComment(long postId, long commentId, CommentDTO commentDTO) {
		
		Post post = postRepository.findById(postId)
				  .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment" ,"id", commentId));
		
		if(comment.getPost().getId() != post.getId())
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Post have no comments with the given commentId");
		
		comment.setBody(commentDTO.getBody());
		comment.setEmail(commentDTO.getEmail());
		comment.setName(commentDTO.getName());
		
		Comment commentResult = commentRepository.save(comment);
		CommentDTO commentDTOResult = mapToDTO(commentResult);
		
		return commentDTOResult;
	}


	@Override
	public void deleteComment(long postId, long commentId) {
		
		Post post = postRepository.findById(postId)
				  .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment" ,"id", commentId));
		
		if(comment.getPost().getId() != post.getId())
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Post have no comments with the given commentId");
		
		commentRepository.delete(comment);
		
	}
	
	private CommentDTO mapToDTO(Comment comment)
	{
		CommentDTO commentDTO = new CommentDTO();
		
		commentDTO.setId(comment.getId());
		commentDTO.setName(comment.getName());
		commentDTO.setEmail(comment.getEmail());
		commentDTO.setBody(comment.getBody());
		
		return commentDTO;
	}
	
	private Comment mapToEntity(CommentDTO commentDTO)
	{
		Comment comment = new Comment();
		
		comment.setName(commentDTO.getName());
		comment.setEmail(commentDTO.getEmail());
		comment.setBody(commentDTO.getBody());
		
		return comment;
	}

}
