package com.niit.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.niit.oracle.DAO.JobDetailDAO;
import com.niit.oracle.model.JobDetail;

@RestController
public class JobRestController {

@Autowired
JobDetailDAO jobdetaildao;

JobDetail jobdetail;
	
}
