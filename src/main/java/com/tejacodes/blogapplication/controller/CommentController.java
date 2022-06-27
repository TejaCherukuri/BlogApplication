package com.tejacodes.blogapplication.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tejacodes.blogapplication.dto.CommentDTO;
import com.tejacodes.blogapplication.service.CommentService;

@RestController
@RequestMapping("/api/posts")
public class CommentController {
	
	private CommentService commentService;
	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}


	@PostMapping("/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@PathVariable("postId") long postId,
													@Valid @RequestBody CommentDTO commentDTO)
	
	{
		CommentDTO commentDTOResult = commentService.createComment(postId, commentDTO);
		return new ResponseEntity<>(commentDTOResult, HttpStatus.CREATED);
	}
	
	@GetMapping("/{postId}/comments")
	public List<CommentDTO> getAllCommentsByPostId(@PathVariable("postId") long postId)
	{
		List<CommentDTO> commentDTOList = commentService.getAllCommentsByPostId(postId);
		return commentDTOList;
	}
	
	@GetMapping("/{postId}/comments/{id}")
	public ResponseEntity<CommentDTO> getCommentByPostIdAndCommentId(@PathVariable("postId") long postId,
																	 @PathVariable("id") long id)
	{
		CommentDTO commentDTO = commentService.getCommentByPostIdAndCommentId(postId, id);
		return new ResponseEntity<>(commentDTO, HttpStatus.OK);
	}
	
	@PutMapping("/{postId}/comments/{id}")
	public ResponseEntity<CommentDTO> updateComment(@PathVariable("postId") long postId,
													@PathVariable("id") long id,
													@Valid @RequestBody CommentDTO commentDTO)
	{
		CommentDTO commentDTOResult = commentService.updateComment(postId, id, commentDTO);
		return new ResponseEntity<>(commentDTOResult, HttpStatus.OK);
	}
	
	@DeleteMapping("/{postId}/comments/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId,
													@PathVariable("id") long id)
	{
		commentService.deleteComment(postId, id);
		return ResponseEntity.ok("Comment is deleted for CommentId : "+id);
	}
}
