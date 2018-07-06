package com.niit.restcontroller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.oracle.DAO.BlogCommentDAO;
import com.niit.oracle.DAO.BlogDAO;
import com.niit.oracle.model.Blog;
import com.niit.oracle.model.BlogComment;



@RestController
public class BlogsRestController
{
	@Autowired
	BlogDAO blogDAO;
     
	@Autowired
	BlogCommentDAO blogCommentDAO;
	 
	@GetMapping("/listBlogs")
	public ResponseEntity<List<Blog>> listBlogs()
	{
		List<Blog> listBlogs=blogDAO.list();
		
		if(listBlogs.size()>0)
		{
			return new ResponseEntity<List<Blog>>(listBlogs,HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<List<Blog>>(listBlogs,HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value="/addBlog")
	public ResponseEntity<String> insertBlog(@RequestBody Blog blog)
	{
		blog.setCreatedate(new java.util.Date());
		blog.setDislikes(0);
		blog.setLikes(0);
		blog.setStatus("NA");
		
		
		
		
		if(blogDAO.add(blog))
		{
			
			
			
			return new ResponseEntity<String>("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getBlog/{blogid}")
	public ResponseEntity<Blog> getBlog(@PathVariable("blogid")int blogid)
	{
		Blog blog=blogDAO.getid(blogid);
		if(blog!=null)
		{
			return new ResponseEntity<Blog>(blog,HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Blog>(blog,HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/approveBlog/{blogid}")
	public ResponseEntity<String> approveBlog(@PathVariable("blogid")int blogid)
	{
		if(blogDAO.approve(blogid))
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/rejectBlog/{blogid}")
	public ResponseEntity<String> rejectBlog(@PathVariable("blogid")int blogid)
	{
		if(blogDAO.reject(blogid))
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/blogLike/{blogid}")
	public ResponseEntity<String> incrementLikes(@PathVariable("blogid")int blogid)
	{
		if(blogDAO.likes(blogid))
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/blogDisLike/{blogid}")
	public ResponseEntity<String> incrementDisLikes(@PathVariable("blogid")int blogid)
	{
		if(blogDAO.dislikes(blogid))
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/deleteBlog/{blogid}")
	public ResponseEntity<String> deleteBlog(@PathVariable("blogid")int blogid)
	{
		Blog blog=blogDAO.getid(blogid);
		BlogComment blogcomment=blogCommentDAO.getid(blogid);
		 
		  
		
		if(blogDAO.delete(blog) && blogCommentDAO.delete(blogcomment))
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/updateBlog/{blogid}")
	public ResponseEntity<String> updateBlog(@PathVariable("blogid") int blogid,@RequestBody Blog blog)
	{
		blog.setBlogid(blogid);
		Blog tblog=blogDAO.getid(blogid);
		blog.setBlogname(tblog.getBlogname());
		blog.setBlogcontent(tblog.getBlogcontent());
		blog.setCreatedate(tblog.getCreatedate());
		blog.setLikes(tblog.getDislikes());
		blog.setDislikes(tblog.getDislikes());
		blog.setStatus(tblog.getStatus());
		
		
		if(blogDAO.update(blog))
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	
@GetMapping("/getBlogComments/{blogid}")
public ResponseEntity<List<BlogComment>> getBlogComments(@PathVariable("blogid") int blogid){
   
	List<BlogComment> list=blogCommentDAO.list();
	
	if(list.size()>0) {
		return new ResponseEntity<List<BlogComment>>(list,HttpStatus.OK);
	}else {
		return new ResponseEntity<List<BlogComment>>(list,HttpStatus.NOT_FOUND);
	}
	
}
	
@PostMapping("/addBlogComment/{blogid}")
public ResponseEntity<String> addBlogComment(@PathVariable("blogid") int blogid,@RequestBody BlogComment blogcomment){
	
	blogcomment.setCommentdate(new Date());
	
	blogcomment.setBlogid(blogid);
	if(blogCommentDAO.add(blogcomment)) {
		  return new  ResponseEntity<String>("Success",HttpStatus.OK); 
	  }else {
		  return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
	  }
}

@PutMapping("/updateBlogComment/{commenid}")
public ResponseEntity<String> updateBlogComment(@PathVariable("commenid") int commenid,@RequestBody BlogComment blogComment)
{
	blogComment.setCommentid(commenid);
	if(blogCommentDAO.update(blogComment)) {
	   return new ResponseEntity<String>("Success",HttpStatus.OK);
   }else {
	   return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
   }
	

}





}
