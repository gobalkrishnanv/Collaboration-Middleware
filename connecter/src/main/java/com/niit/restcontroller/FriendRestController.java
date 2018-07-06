package com.niit.restcontroller;

import java.util.List;

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

@RestController
public class FriendRestController {

@Autowired
FriendDAO frienddao;

Friend friend;
	
@PostMapping("/sendFriendRequest")
public ResponseEntity<String> sendFriendRequest( @RequestBody Friend friend){
	friend.setStatus("NA");
	
	if(frienddao.add(friend)) {
		return new ResponseEntity<String>("Success",HttpStatus.OK);
	}else {
		return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
	}
	
}

@GetMapping("/deleteFriendRequest/{friendloginname}")
public ResponseEntity<String> deleteFriendRequest(@PathVariable("friendloginname") String friendloginname){
	Friend friend=frienddao.getfriendloginname(friendloginname);
	
	
	if(frienddao.delete(friend)){
		return new ResponseEntity<String>("Success",HttpStatus.OK);
	}else {
		return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
	}
}

@GetMapping("/acceptFriendReqest/{friendloginname}")
public ResponseEntity<String> accpeptFriendRequest(@PathVariable("friendloginname") String friendloginname){
	Friend friend=frienddao.getfriendloginname(friendloginname);
	
	if(frienddao.aspectRequest(friend.getFriendid())) {
		return new ResponseEntity<String>("Success",HttpStatus.OK);
	}else {
		return new ResponseEntity<String>("Failure",HttpStatus.NOT_FOUND);
	}
}





}
