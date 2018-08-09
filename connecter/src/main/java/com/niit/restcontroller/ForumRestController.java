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
import org.springframework.web.bind.annotation.RestController;

import com.niit.oracle.DAO.ForumCommentDAO;
import com.niit.oracle.DAO.ForumDAO;
import com.niit.oracle.model.Forum;
import com.niit.oracle.model.ForumComment;
import com.niit.oracle.model.UserDetail;



@RestController
public class ForumRestController
{
	@Autowired
	ForumDAO forumDAO;
     
	@Autowired
	ForumCommentDAO forumCommentDAO;
	
	
	
	@GetMapping("/listForums")
	public ResponseEntity<List<Forum>> listForum()
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
	public ResponseEntity<String> insertForum(@RequestBody Forum forum,HttpSession session)
	{
		
		 UserDetail login=(UserDetail)session.getAttribute("userDetail");	
		  forum.setLoginname(login.getLoginname());
		
		
		forum.setCreatedate(new java.util.Date());
		forum.setDislikes(0);
		forum.setLikes(0);
		forum.setStatus("NA");
		
		
		
		
		if(forumDAO.add(forum))
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
	public ResponseEntity<String> ForumLikes(@PathVariable("forumid")int forumid)
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
	public ResponseEntity<String> ForumDisLikes(@PathVariable("forumid")int forumid)
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
	
	@GetMapping("/deleteForum/{forumid}")
	public ResponseEntity<String> deleteForum(@PathVariable("forumid")int forumid)
	{
		Forum forum=forumDAO.getid(forumid);
		
		List<ForumComment> list=forumCommentDAO.list();
		
		//delete Forum and Forumcomment for a Forum;
		for(ForumComment forumcomment:list) {
			if(forumcomment.getForumid()==forumid) {
				   
				    forumCommentDAO.delete(forumcomment);
             }
		}
		  
		
		if(forumDAO.delete(forum))
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	@GetMapping("/updateForum")
	
	public String redirectupdatepage(Model m){
		
		
		return "redirect:/updateforum";
		
	}
	@PutMapping("/updateForum/{forumid}")
	public ResponseEntity<String> updateForum(@PathVariable("forumid") int forumid,@RequestBody Forum forum)
	{
		Forum tforum=forumDAO.getid(forumid);
		
		forum.setForumid(tforum.getForumid());
		forum.setLoginname(tforum.getLoginname());
		forum.setCreatedate(tforum.getCreatedate());
		forum.setLikes(tforum.getDislikes());
		forum.setDislikes(tforum.getDislikes());
		forum.setStatus(tforum.getStatus());
		
		
		if(forumDAO.update(forum))
		{
			return new ResponseEntity("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	
	@GetMapping("/getForumComments")
	public ResponseEntity<List<ForumComment>> getForumAllComments(){
	   
		List<ForumComment> list=forumCommentDAO.list();
		
	    
				
		
		if(list.size()>0) {
			return new ResponseEntity<List<ForumComment>>(list,HttpStatus.OK);
		}else {
			return new ResponseEntity<List<ForumComment>>(list,HttpStatus.NOT_FOUND);
		}
		
	}	
	
	
	
@GetMapping("/getForumComments/{forumid}")
public ResponseEntity<List<ForumComment>> getForumComments(@PathVariable("forumid") int forumid){
   
	List<ForumComment> list=forumCommentDAO.list();
	
	
	List<ForumComment> temp=new ArrayList<ForumComment>();
	
	temp.removeAll(temp);
	
	for(ForumComment forumcomment:list) {
		if(forumcomment.getForumid()==forumid) {
			temp.add(forumcomment);
		}
		
	}
	
	
	if(list.size()>0) {
		return new ResponseEntity<List<ForumComment>>(temp,HttpStatus.OK);
	}else {
		return new ResponseEntity<List<ForumComment>>(temp,HttpStatus.NOT_FOUND);
	}
	
}
	
@PostMapping("/addForumComment/{forumid}")
public ResponseEntity<String> addForumComment(@PathVariable("forumid") int forumid,@RequestBody ForumComment forumcomment,HttpSession session){
	
	
	UserDetail login=(UserDetail)session.getAttribute("userDetail");	
	forumcomment.setLoginname(login.getLoginname());
	
	
	forumcomment.setDiscussionDate(new Date());
	
	forumcomment.setForumid(forumid);
	
	
	
	if(forumCommentDAO.add(forumcomment)) {
		  return new  ResponseEntity<String>("Success",HttpStatus.OK); 
	  }else {
		  return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
	  }
}

@PutMapping("/updateForumComment/{commenid}")
public ResponseEntity<String> updateForumComment(@PathVariable("commenid") int commenid,@RequestBody ForumComment ForumComment)
{
	ForumComment.setCommentid(commenid);
	ForumComment tem=forumCommentDAO.getid(commenid);
	ForumComment.setForumid(tem.getForumid());
	ForumComment.setLoginname(tem.getLoginname());
	ForumComment.setDiscussionDate(tem.getDiscussionDate());
	
	
	if(forumCommentDAO.update(ForumComment)) {
	   return new ResponseEntity<String>("Success",HttpStatus.OK);
   }else {
	   return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
   }
	

}

@GetMapping("/deleteForumComment/{commentid}")
public ResponseEntity<String> deleteForumComment(@PathVariable("commentid") int commentid){
	ForumComment Forumcomment=forumCommentDAO.getid(commentid);
	if(forumCommentDAO.delete(Forumcomment)) {
		return new ResponseEntity<String>("OK",HttpStatus.OK);
	}else {
		return new ResponseEntity<String>("NOT",HttpStatus.NOT_FOUND);
	}
}

@GetMapping("/getfcomment/{commentid}")
public ResponseEntity<ForumComment> getfcomment(@PathVariable("commentid") int commentid){
	ForumComment get=forumCommentDAO.getid(commentid);
	if(get!=null) {
		return new ResponseEntity<ForumComment>(get,HttpStatus.OK);
	}else {
		return new ResponseEntity<ForumComment>(get,HttpStatus.NOT_FOUND);
	}
} 


}
