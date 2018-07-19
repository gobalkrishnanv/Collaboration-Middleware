package com.niit.restcontroller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.oracle.DAO.UserDetailDAO;
import com.niit.oracle.model.UserDetail;



@RestController
public class UserRestController 
{

	@Autowired
	UserDetailDAO userDAO;
	
	@PostMapping("/registerUser")
	public ResponseEntity<String> registerUser(@RequestBody UserDetail userDetail)
	{
		userDetail.setRole("ROLE_USER");
		
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
		if(tempUserDetail!=null)
		{
			session.setAttribute("userDetail", tempUserDetail);
			return new ResponseEntity<UserDetail>(tempUserDetail,HttpStatus.OK);
			
		}
		else
		{
			return new ResponseEntity<UserDetail>(tempUserDetail,HttpStatus.NOT_FOUND);
		}
	}
	
}
