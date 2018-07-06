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

import com.niit.oracle.DAO.ForumCommentDAO;
import com.niit.oracle.DAO.ForumDAO;
import com.niit.oracle.model.Forum;
import com.niit.oracle.model.ForumComment;



@RestController
public class ForumRestController
{
	@Autowired
	ForumDAO forumDAO;
     
	@Autowired
	ForumCommentDAO forumCommentDAO;
	 
	@GetMapping("/listForums")
	public ResponseEntity<List<Forum>> listForums()
	{
		List<Forum> listForums=forumDAO.list();
		
		if(listForums.size()>0)
		{
			return new ResponseEntity<List<Forum>>(listForums,HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<List<Forum>>(listForums,HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value="/addForum")
	public ResponseEntity<String> insertForum(@RequestBody Forum Forum)
	{
		Forum.setCreatedate(new java.util.Date());
		Forum.setDislikes(0);
		Forum.setLikes(0);
		Forum.setStatus("NA");
		
		
		
		
		if(forumDAO.add(Forum))
		{
			
			
			
			return new ResponseEntity<String>("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getForum/{forumid}")
	public ResponseEntity<Forum> getForum(@PathVariable("forumid")int forumid)
	{
		Forum forum=forumDAO.getid(forumid);
		if(forum!=null)
		{
			return new ResponseEntity<Forum>(forum,HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<Forum>(forum,HttpStatus.NOT_FOUND);
		}
		
	}
	
	@GetMapping("/approveForum/{forumid}")
	public ResponseEntity<String> approveForum(@PathVariable("forumid")int forumid)
	{
		if(forumDAO.approve(forumid)) 
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/rejectForum/{forumid}")
	public ResponseEntity<String> rejectForum(@PathVariable("forumid")int forumid)
	{
		if(forumDAO.reject(forumid))
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/forumLike/{forumid}")
	public ResponseEntity<String> forumLike(@PathVariable("forumid")int forumid)
	{
		if(forumDAO.likes(forumid))
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/forumDisLike/{forumid}")
	public ResponseEntity<String> forumdislike(@PathVariable("forumid")int forumid)
	{
		if(forumDAO.dislikes(forumid))
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/deleteforum/{forumid}")
	public ResponseEntity<String> deleteforum(@PathVariable("forumid")int forumid)
	{
		Forum blog=forumDAO.getid(forumid);
		ForumComment blogcomment=forumCommentDAO.getid(forumid);
		 
		  
		
		if(forumDAO.delete(blog) && forumCommentDAO.delete(blogcomment))
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/updateForum/{forumid}")
	public ResponseEntity<String> updateforum(@PathVariable("forumid") int forumid,@RequestBody Forum forum)
	{
		forum.setForumid(forumid);
		if(forumDAO.update(forum))
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	
@GetMapping("/getForumComments/{forumid}")
public ResponseEntity<List<ForumComment>> getForumComments(@PathVariable("forumid") int forumid){
   
	List<ForumComment> list=forumCommentDAO.list();
	
	if(list.size()>0) {
		return new ResponseEntity<List<ForumComment>>(list,HttpStatus.OK);
	}else {
		return new ResponseEntity<List<ForumComment>>(list,HttpStatus.NOT_FOUND);
	}
	
}
	
@PostMapping("/addForumComment/{forumid}")
public ResponseEntity<String> addForumComment(@PathVariable("forumid") int forumid,@RequestBody ForumComment forumcomment){
	
	forumcomment.setDiscussionDate(new Date());
	
	forumcomment.setForumid(forumid);
	if(forumCommentDAO.add(forumcomment)) {
		  return new  ResponseEntity<String>("Success",HttpStatus.OK); 
	  }else {
		  return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
	  }
}

@PutMapping("/updateForumComment/{commenid}")
public ResponseEntity<String> updateForumComment(@PathVariable("commenid") int commenid,@RequestBody ForumComment forumComment)
{
	forumComment.setCommentid(commenid);
	if(forumCommentDAO.update(forumComment)) {
	   return new ResponseEntity<String>("Success",HttpStatus.OK);
   }else {
	   return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
   }
	

}





}
