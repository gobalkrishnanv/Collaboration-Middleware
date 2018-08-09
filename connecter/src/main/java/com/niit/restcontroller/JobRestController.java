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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.oracle.DAO.JobDetailDAO;
import com.niit.oracle.model.JobDetail;
import com.niit.oracle.model.UserDetail;

@RestController
public class JobRestController {

@Autowired
JobDetailDAO jobdetaildao;

JobDetail jobdetail;

@GetMapping("/jobs")
public ResponseEntity<List<JobDetail>> joblist(){
	List<JobDetail> jobs=jobdetaildao.list();
	if(jobs.size()>0) {
		return new ResponseEntity<List<JobDetail>>(jobs,HttpStatus.OK);
	}else {
		return new ResponseEntity<List<JobDetail>>(jobs,HttpStatus.NOT_FOUND);
	}
	
}
@PostMapping("/addjob")
public ResponseEntity<String> postjob(@RequestBody JobDetail jobdetail,HttpSession session){
	UserDetail login=(UserDetail)session.getAttribute("userDetail");	
	jobdetail.setLoginname(login.getLoginname());
	
	
	jobdetail.setRole("admin");
    
	jobdetail.setLastdate(new Date());
	jobdetail.setResponse("NA");
	
	
	if(jobdetaildao.add(jobdetail)) {
		return new ResponseEntity<String>("OK",HttpStatus.OK);
	}else {
		return new ResponseEntity<String>("NOT",HttpStatus.NOT_FOUND);
	}
}

@PutMapping("/updatejob/{jobid}")
public ResponseEntity<String> updatejob(@PathVariable("jobid") int jobid,@RequestBody JobDetail jobdetail){
	JobDetail temp=jobdetaildao.getid(jobid);
    
	jobdetail.setJobid(jobid);
	jobdetail.setLoginname(temp.getLoginname());
	jobdetail.setRole(temp.getRole());
	jobdetail.setResponse(temp.getResponse());
	jobdetail.setLastdate(temp.getLastdate());
	if(jobdetaildao.update(jobdetail)) {
		return new ResponseEntity<String>("OK",HttpStatus.OK);
	}else {
		return new ResponseEntity<String>("NOT",HttpStatus.NOT_FOUND);
	}
}
	
@GetMapping("/deletejob/{jobid}")
public ResponseEntity<String> deletejob(@PathVariable("jobid") int jobid){
	JobDetail temp=jobdetaildao.getid(jobid);
	
	if(jobdetaildao.delete(temp)) {
		return new ResponseEntity<String>("OK",HttpStatus.OK);
	}else {
		return new ResponseEntity<String>("NOT",HttpStatus.NOT_FOUND);
	}
}



@GetMapping("/applyjob/{jobid}")
public ResponseEntity<String> applyjob(@PathVariable("jobid") int jobid){
	if(jobdetaildao.apply(jobid)) {
		return new ResponseEntity<String>("OK",HttpStatus.OK);
	}else {
		return new ResponseEntity<String>("NOT",HttpStatus.NOT_FOUND);
	}
}

@GetMapping("/rejectjob/{jobid}")
public ResponseEntity<String> rejectjob(@PathVariable("jobid") int jobid){
	
	if(jobdetaildao.reject(jobid)) {
		return new ResponseEntity<String>("OK",HttpStatus.OK);
	}else {
		return new ResponseEntity<String>("NOT",HttpStatus.NOT_FOUND);
	}
}

}
