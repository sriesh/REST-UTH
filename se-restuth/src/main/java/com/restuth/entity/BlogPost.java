package com.restuth.entity;

public class BlogPost{
	
	
	public BlogPost()
	{}
	
	String content;
	String author;
	String id;
	String title;
	
	public BlogPost(String id,String title, String author)
	{
		this.id=id;
		this.title=title;
		this.author=author;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
