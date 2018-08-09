package com.niit.restcontroller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.oracle.DAO.BlogCommentDAO;
import com.niit.oracle.DAO.BlogDAO;
import com.niit.oracle.model.Blog;
import com.niit.oracle.model.BlogComment;
import com.niit.oracle.model.UserDetail;



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
	public ResponseEntity<String> insertBlog(@RequestBody Blog blog,HttpSession session)
	{
		
		 UserDetail login=(UserDetail)session.getAttribute("userDetail");	
		 blog.setLoginname(login.getLoginname());
		
		
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
		
		List<BlogComment> list=blogCommentDAO.list();
		
		//delete blog and blogcomment for a blog;
		for(BlogComment blogcomment:list) {
			if(blogcomment.getBlogid()==blogid) {
				   
				    blogCommentDAO.delete(blogcomment);
             }
		}
		  
		
		if(blogDAO.delete(blog))
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	@GetMapping("/updateBlog")
	
	public String redirectupdatepage(Model m){
		
		
		return "redirect:/updateBlog";
		
	}
	@PutMapping("/updateBlog/{blogid}")
	public ResponseEntity<String> updateBlog(@PathVariable("blogid") int blogid,@RequestBody Blog blog)
	{
		Blog tblog=blogDAO.getid(blogid);
		
		blog.setBlogid(tblog.getBlogid());
		blog.setLoginname(tblog.getLoginname());
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
	
	
	@GetMapping("/getBlogComments")
	public ResponseEntity<List<BlogComment>> getBlogAllComments(){
	   
		List<BlogComment> list=blogCommentDAO.list();
		
	    
				
		
		if(list.size()>0) {
			return new ResponseEntity<List<BlogComment>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<List<BlogComment>>(list,HttpStatus.NOT_FOUND);
		}
		
	}	
	
	
	
@GetMapping("/getBlogComments/{blogid}")
public ResponseEntity<List<BlogComment>> getBlogComments(@PathVariable("blogid") int blogid){
   
	List<BlogComment> list=blogCommentDAO.list();
	
	
	List<BlogComment> temp=new ArrayList<BlogComment>();
	
	temp.removeAll(temp);
	
	for(BlogComment blogcomment:list) {
		if(blogcomment.getBlogid()==blogid) {
			temp.add(blogcomment);
		}
		
	}
	
	
	if(list.size()>0) {
		return new ResponseEntity<List<BlogComment>>(temp,HttpStatus.OK);
	}else {
		return new ResponseEntity<List<BlogComment>>(temp,HttpStatus.NOT_FOUND);
	}
	
}
	
@PostMapping("/addBlogComment/{blogid}")
public ResponseEntity<String> addBlogComment(@PathVariable("blogid") int blogid,@RequestBody BlogComment blogcomment,HttpSession session){
	
	
	
	
	blogcomment.setCommentdate(new Date());
	
	blogcomment.setBlogid(blogid);
    UserDetail login=(UserDetail)session.getAttribute("userDetail");	
	blogcomment.setLoginname(login.getLoginname());
	
	
	
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
	BlogComment tem=blogCommentDAO.getid(commenid);
	blogComment.setBlogid(tem.getBlogid());
	blogComment.setLoginname(tem.getLoginname());
	blogComment.setCommentdate(tem.getCommentdate());
	
	
	if(blogCommentDAO.update(blogComment)) {
	   return new ResponseEntity<String>("Success",HttpStatus.OK);
   }else {
	   return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
   }
	

}

@GetMapping("/deleteBlogComment/{commentid}")
public ResponseEntity<String> deleteBlogComment(@PathVariable("commentid") int commentid){
	BlogComment blogcomment=blogCommentDAO.getid(commentid);
	if(blogCommentDAO.delete(blogcomment)) {
		return new ResponseEntity<String>("OK",HttpStatus.OK);
	}else {
		return new ResponseEntity<String>("NOT",HttpStatus.NOT_FOUND);
	}
}

@GetMapping("/getcomment/{commentid}")
public ResponseEntity<BlogComment> getcomment(@PathVariable("commentid") int commentid){
	BlogComment get=blogCommentDAO.getid(commentid);
	if(get!=null) {
		return new ResponseEntity<BlogComment>(get,HttpStatus.OK);
	}else {
		return new ResponseEntity<BlogComment>(get,HttpStatus.NOT_FOUND);
	}
} 


}
