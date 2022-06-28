package com.tejacodes.blogapplication.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.tejacodes.blogapplication.dto.CommentDTO;
import com.tejacodes.blogapplication.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "CRUD REST APIs for Comment Respource")
@RestController
@RequestMapping("/api/v1/posts")
public class CommentController {
	
	private CommentService commentService;
	
	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	// Only Admin is allowed to create a comment
	@ApiOperation(value = "Create Comment REST API")
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{postId}/comments")
	public ResponseEntity<CommentDTO> createComment(@PathVariable("postId") long postId,
													@Valid @RequestBody CommentDTO commentDTO)
	
	{
		CommentDTO commentDTOResult = commentService.createComment(postId, commentDTO);
		return new ResponseEntity<>(commentDTOResult, HttpStatus.CREATED);
	}
	
	
	@ApiOperation(value = "Get All Comments By Post ID REST API")
	@GetMapping("/{postId}/comments")
	public List<CommentDTO> getAllCommentsByPostId(@PathVariable("postId") long postId)
	{
		List<CommentDTO> commentDTOList = commentService.getAllCommentsByPostId(postId);
		return commentDTOList;
	}
	
	@ApiOperation(value = "Get Single Comment By ID REST API")
	@GetMapping("/{postId}/comments/{id}")
	public ResponseEntity<CommentDTO> getCommentByPostIdAndCommentId(@PathVariable("postId") long postId,
																	 @PathVariable("id") long id)
	{
		CommentDTO commentDTO = commentService.getCommentByPostIdAndCommentId(postId, id);
		return new ResponseEntity<>(commentDTO, HttpStatus.OK);
	}
	
	// Only Admin is allowed to update a comment
	@ApiOperation(value = "Update Comment By ID REST API")
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{postId}/comments/{id}")
	public ResponseEntity<CommentDTO> updateComment(@PathVariable("postId") long postId,
													@PathVariable("id") long id,
													@Valid @RequestBody CommentDTO commentDTO)
	{
		CommentDTO commentDTOResult = commentService.updateComment(postId, id, commentDTO);
		return new ResponseEntity<>(commentDTOResult, HttpStatus.OK);
	}
	
	// Only Admin is allowed to delete a comment
	@ApiOperation(value = "Delete Comment By ID REST API")
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{postId}/comments/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId,
													@PathVariable("id") long id)
	{
		commentService.deleteComment(postId, id);
		return ResponseEntity.ok("Comment is deleted for CommentId : "+id);
	}
}
