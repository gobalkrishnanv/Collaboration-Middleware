package com.niit.restcontroller;

import java.util.List;

import com.niit.oracle.model.Blog;
import com.niit.oracle.model.BlogComment;

public class BlogArrange {

	public Blog blog;
	public List<BlogComment> commentlist;
	
	public void set(Blog b,List<BlogComment> c) {
		this.blog=b;
		this.commentlist=c;
	}
	public Blog blog() {return this.blog;}
	public List<BlogComment> blogcommentlist(){return this.commentlist;}
	
	
	public String toString() {
		
		StringBuilder s=new StringBuilder();
		s.append("--------------------BLOG-----------------------"+blog+"------------------------------Blogcommentlist"+commentlist);
		return s.toString();
	}
	
}
