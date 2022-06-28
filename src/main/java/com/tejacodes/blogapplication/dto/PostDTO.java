package com.tejacodes.blogapplication.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Post model information")
public class PostDTO {
	
	@ApiModelProperty(value = "Blog post id")
	private long id;
	
	@ApiModelProperty(value = "Blog post title")
	@NotEmpty
	@Size(min = 2, message = "Post title should have atleast 2 characters")
	private String title;
	
	@ApiModelProperty(value = "Blog post description")
	@NotEmpty
	@Size(min = 10, message = "Post Description should have atleast 10 characters")
	private String description;
	
	@ApiModelProperty(value = "Blog post content")
	@NotEmpty
	private String content;
	
	@ApiModelProperty(value = "Blog post comments")
	private Set<CommentDTO> comments;
	
	public PostDTO() {}
	
	public PostDTO(long id, String title, String description, String content) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.content = content;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Set<CommentDTO> getComments() {
		return comments;
	}
	public void setComments(Set<CommentDTO> comments) {
		this.comments = comments;
	}
	
}
