package com.niit.restcontroller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.oracle.DAO.FriendDAO;
import com.niit.oracle.DAO.UserDetailDAO;
import com.niit.oracle.model.Friend;
import com.niit.oracle.model.UserDetail;



@RestController
public class UserRestController 
{

	@Autowired
	UserDetailDAO userDAO;
	@Autowired
	FriendDAO frienddao;
	@PostMapping("/registerUser")
	public ResponseEntity<String> registerUser(@RequestBody UserDetail userDetail)
	{
		userDetail.setRole("user");
		userDetail.setRegister(new Date());

		if(userDetail!=null)
		{
			
			Friend friend=new Friend();
			friend.setLoginname(userDetail.getLoginname());
			friend.setFriendloginname("");
			friend.setStatus("");
			frienddao.add(friend);

		}
		
		if(userDAO.add(userDetail))
		{
			
			return new ResponseEntity<String>("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
		}
		
	}
		
	@PostMapping("/registerAdmin")
	public ResponseEntity<String> registerAdmin(@RequestBody UserDetail userDetail)
	{
		userDetail.setRole("admin");
		userDetail.setRegister(new Date());
		
		if(userDAO.add(userDetail))
		{
			return new ResponseEntity<String>("Success",HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
		}
	}
	
	
		
	
	@PostMapping("/checkLogin")
	public ResponseEntity<UserDetail> checkLogin(@RequestBody UserDetail userDetail,HttpSession session)
	{
		UserDetail tempUserDetail=userDAO.getloginname(userDetail.getLoginname());
		System.out.println(tempUserDetail);
		
		
         if(tempUserDetail!=null) {
			session.setAttribute("userDetail", tempUserDetail);
			return new ResponseEntity<UserDetail>(tempUserDetail,HttpStatus.OK);
			
		}
		else
		{
			return new ResponseEntity<UserDetail>(tempUserDetail,HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/updateuser/{id}")
	public ResponseEntity<String> updateuser(@PathVariable("id") int id, @RequestBody UserDetail userDetail)
	{
         UserDetail ut=userDAO.getid(id);
         userDetail.setId(id);
         userDetail.setRole(ut.getRole());
         userDetail.setRegister(ut.getRegister());
		
         if(userDAO.update(userDetail))
		{
		    return new ResponseEntity<String>("OK",HttpStatus.OK);
			
		}
		else
		{
			return new ResponseEntity<String>("NOT",HttpStatus.NOT_FOUND);
		}
	}
	
    @GetMapping("/get/{id}")
    public ResponseEntity<UserDetail> get(@PathVariable("id") int id){
    	UserDetail us=userDAO.getid(id);
    	if(us!=null)
		{
		    return new ResponseEntity<UserDetail>(us,HttpStatus.OK);
			
		}
		else
		{
			return new ResponseEntity<UserDetail>(us,HttpStatus.NOT_FOUND);
		}
    	
    }
    
    @GetMapping("/getusers")
    public ResponseEntity<List<UserDetail>> getusers(){
    	List<UserDetail> us=userDAO.list();
    	if(us!=null)
		{
		    return new ResponseEntity<List<UserDetail>>(us,HttpStatus.OK);
			
		}
		else
		{
			return new ResponseEntity<List<UserDetail>>(us,HttpStatus.NOT_FOUND);
		}
    	
    }
   
    @GetMapping("/deleteuser/{id}")
    public ResponseEntity<String> deleteUsers(@PathVariable("id") int id){
    	
    	UserDetail ut=userDAO.getid(id);
        if(userDAO.delete(ut))
		{
		    return new ResponseEntity<String>("OK",HttpStatus.OK);
		}
		else
		{
		 return new ResponseEntity<String>("NOT",HttpStatus.NOT_FOUND);
		}
    	 
    }
   
    
  
}
