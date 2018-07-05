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
public class ForumRestController {
	@Autowired
	ForumDAO forumdao;
     
	@Autowired
	ForumCommentDAO forumcommentdao;
	 
	@GetMapping("/listForums")
	public ResponseEntity<List<Forum>> listForums()
	{
		List<Forum> listforums=forumdao.list();
		
		if(listforums.size()>0)
		{
			return new ResponseEntity<List<Forum>>(listforums,HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<List<Forum>>(listforums,HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping(value="/addForum")
	public ResponseEntity<String> insertForum(@RequestBody Forum forum)
	{
		forum.setCreatedate(new java.util.Date());
		forum.setLikes(0);
		forum.setDislikes(0);
		forum.setStatus("NA");
		
		
		
		
		if(forumdao.add(forum))
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
		Forum forum=forumdao.getid(forumid);
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
		if(forumdao.approve(forumid))
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
		if(forumdao.reject(forumid))
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/incrementLikes/{forumid}")
	public ResponseEntity<String> incrementLikes(@PathVariable("forumid")int forumid)
	{
		if(forumdao.likes(forumid))
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/incrementDisLikes/{forumid}")
	public ResponseEntity<String> incrementDisLikes(@PathVariable("forumid")int forumid)
	{
		if(forumdao.dislikes(forumid))
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/deleteForum/{forumid}")
	public ResponseEntity<String> deleteForum(@PathVariable("forumid")int forumid)
	{
		Forum forum=forumdao.getid(forumid);
		ForumComment forumcomment=forumcommentdao.getid(forumid);
		 
		  
		
		if(forumdao.delete(forum) && forumcommentdao.delete(forumcomment))
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/updateForum")
	public ResponseEntity<String> updateForum(@RequestBody Forum forum)
	{
		if(forumdao.update(forum))
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	
@GetMapping("/getforumComments/{forumid}")
public ResponseEntity<List<ForumComment>> getComments(@PathVariable("blogid") int blogid){
   
	List<ForumComment> list=forumcommentdao.list();
	
	if(list.size()>0) {
		return new ResponseEntity<List<ForumComment>>(list,HttpStatus.OK);
	}else {
		return new ResponseEntity<List<ForumComment>>(list,HttpStatus.NOT_FOUND);
	}
	
}
	
@PostMapping("/addForumComment")
public ResponseEntity<String> addComment(@RequestBody ForumComment forumcomment){
	
	 
	if(forumcommentdao.add(forumcomment)) {
		  return new  ResponseEntity<String>("Success",HttpStatus.OK); 
	  }else {
		  return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
	  }
}

@PutMapping("/updateForumComment")
public ResponseEntity<String> deleteComment(@RequestBody ForumComment forumcomment)
{
   if(forumcommentdao.update(forumcomment)) {
	   return new ResponseEntity<String>("Success",HttpStatus.OK);
   }else {
	   return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
   }
	

}







}
