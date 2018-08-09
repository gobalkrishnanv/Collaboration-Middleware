package com.niit.restcontroller;

import java.util.ArrayList;
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
import com.niit.oracle.model.Friend;
import com.niit.oracle.model.UserDetail;

@RestController
public class FriendRestController {

@Autowired
FriendDAO frienddao;

	

@PostMapping("/friend")
public ResponseEntity<String> addloginname(@RequestBody Friend friend,HttpSession session){
	
	UserDetail login= (UserDetail)session.getAttribute("userDetail");
	
	friend.setFriendid(0);
	friend.setLoginname(login.getLoginname());
   	session.setAttribute("friend", friend);
	if(frienddao.add(friend)) {
	return new ResponseEntity<String>("OK",HttpStatus.OK);
	}else {
	return	new ResponseEntity<String>("NOT",HttpStatus.NOT_FOUND);
	}
}

 
@PostMapping("/sendFriendRequest/{from}/{to}")
public ResponseEntity<String> sendFriendRequest(@PathVariable("from") int from,@PathVariable("to") int to){
	
	if(frienddao.sendRequest(from,to)) {
		return new ResponseEntity<String>("OK",HttpStatus.OK);
	}else {
		return new ResponseEntity<String>("NOT",HttpStatus.NOT_FOUND);
	}
}

@GetMapping("/acceptFriendRequest/{friendid}")
public ResponseEntity<String> acceptFriendRequest(@PathVariable("friendid") int friendid){
	
    
	
	if(frienddao.aspectRequest(friendid)) {
		return new ResponseEntity<String>("OK",HttpStatus.OK);
	}else {
		return new ResponseEntity<String>("NOT",HttpStatus.NOT_FOUND);
	}
}

@GetMapping("/rejectFriendRequest/{friendid}")
public ResponseEntity<String> rejectFriendRequest(@PathVariable("friendid") int friendid){
   
	
    if(frienddao.rejectRequest(friendid)) {
		return new ResponseEntity<String>("OK",HttpStatus.OK);
	}else {
		return new ResponseEntity<String>("NOT",HttpStatus.NOT_FOUND);
	}
}




@GetMapping("/deleteFriendRequest/{friendid}")
public ResponseEntity<String> deleteFriendRequest(@PathVariable("friendid") int  friendid){
	Friend friend=frienddao.getid(friendid);	
	
	if(frienddao.delete(friend)){
		return new ResponseEntity<String>("Success",HttpStatus.OK);
	}else {
		return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
	}
}

@GetMapping("/friendlist/{name}")
public ResponseEntity<List<Friend>> friendlist(@PathVariable("name") String name){
	
	List<Friend> list=frienddao.list();
	
	
	List<Friend> temp=new ArrayList<Friend>();
    temp.removeAll(temp);
	
	for(Friend friend:list) {
		if(friend.getStatus()!=null) {
		if((friend.getLoginname().equals(name))&&(friend.getStatus().equals("A"))) {
			temp.add(friend);
		}
		}
	}
	
	if(temp.size()>0) {
		return new ResponseEntity<List<Friend>>(temp,HttpStatus.OK);
	}else {
		return new ResponseEntity<List<Friend>>(temp,HttpStatus.NOT_FOUND);
	}
	
	
	
	
}


@GetMapping("/suggestedfriendlist/{name}")
public ResponseEntity<List<Friend>> suggestedFriendlist(@PathVariable("name") String name){
	
	List<Friend> list=frienddao.list();
		
	List<Friend> temp=new ArrayList<Friend>();
    temp.removeAll(temp);
	
	for(Friend friend:list) {
	//System.out.println(friend.getStatus()==null+"---------------");	
	//System.out.println(friend.getLoginname().compareTo(name)+"---------------");
		if((friend.getLoginname().compareTo(name)!=0)&&((friend.getStatus()==null))) {
			temp.add(friend);
		}
		
	}
	
	if(temp.size()>0) {
		return new ResponseEntity<List<Friend>>(temp,HttpStatus.OK);
	}else {
		return new ResponseEntity<List<Friend>>(temp,HttpStatus.NOT_FOUND);
	}
	
	
	
	
}

@GetMapping("/requestfriendlist/{name}")
public ResponseEntity<List<Friend>> requestFriendlist(@PathVariable("name") String name){
	
	List<Friend> list=frienddao.list();
	
	
	List<Friend> temp=new ArrayList<Friend>();
    temp.removeAll(temp);
	
	for(Friend friend:list) {
		System.out.println((friend.getLoginname().compareTo(name))+"------");
		
		if(friend.getStatus()!=null) {
			
			System.out.println(friend.getStatus().equals("NA")+"*************");
			if((friend.getLoginname().compareTo(name)==0)&&(friend.getStatus().equals("NA"))) {
				temp.add(friend);
			}
		}
		
		
		
		
	}
	
	if(temp.size()>0) {
		return new ResponseEntity<List<Friend>>(temp,HttpStatus.OK);
	}else {
		return new ResponseEntity<List<Friend>>(temp,HttpStatus.NOT_FOUND);
	}
	
	
	
	
}


@GetMapping("/getfriend/{name}")
public ResponseEntity<Friend> getfriend(@PathVariable("name") String name){
	List<Friend> fs=frienddao.list();
	int i=0,j=0;
	Friend temp=null;
	for(Friend f:fs) {
	 if(f.getLoginname().equals(name)) {
		 temp=f;
	 }
	}
	if(temp!=null) {
		return new ResponseEntity<Friend>(temp,HttpStatus.OK);
	}else {
		return new ResponseEntity<Friend>(temp,HttpStatus.NOT_FOUND);
	}
}

}
