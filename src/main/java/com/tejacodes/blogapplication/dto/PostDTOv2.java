package com.tejacodes.blogapplication.dto;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostDTOv2 {
	
private long id;
	
	@NotEmpty
	@Size(min = 2, message = "Post title should have atleast 2 characters")
	private String title;
	
	@NotEmpty
	@Size(min = 10, message = "Post Description should have atleast 10 characters")
	private String description;
	
	@NotEmpty
	private String content;
	
	private Set<CommentDTO> comments;
	
	private List<String> tags;
	
	public PostDTOv2() {}
	
	public PostDTOv2(long id, String title, String description, String content, Set<CommentDTO> comments, List<String> tags) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.content = content;
		this.comments = comments;
		this.setTags(tags);
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
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}
