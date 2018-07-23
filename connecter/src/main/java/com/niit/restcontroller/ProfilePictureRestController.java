package com.niit.restcontroller;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.niit.oracle.DAO.ProfilePictureDAO;
import com.niit.oracle.model.ProfilePicture;
import com.niit.oracle.model.UserDetail;

@RestController
public class ProfilePictureRestController {

	@Autowired
	ProfilePictureDAO profilepicturedao;
	
	@RequestMapping(value="/doUpload",method=RequestMethod.POST)
	public ResponseEntity<?> uploadprofilepicture(@RequestParam(value="file") CommonsMultipartFile mulitpart,HttpSession httpsession)
	{
		
		UserDetail userdetail=(UserDetail) httpsession.getAttribute("userDetail");
		
		if(userdetail==null) {
			return new ResponseEntity<String>("unauthorized user",HttpStatus.NOT_FOUND);
			
		}else {
			ProfilePicture profilepicture=new ProfilePicture();
			profilepicture.setImagestream(mulitpart.getBytes());
			System.out.println("I do upload");
			profilepicturedao.add(profilepicture);
			return new ResponseEntity<Void>(HttpStatus.OK);
			
		}
		
		
		
		
	}
	
	@RequestMapping(value="/getImage/{loginname}",method=RequestMethod.GET)
	public @ResponseBody byte[] getprofilepicture(@PathVariable("loginname") UserDetail loginname,HttpSession httpsession) {
		
		UserDetail userdetail=(UserDetail) httpsession.getAttribute("userDetail");
		if(userdetail==null) {
			return null;
		}else {
			ProfilePicture profilepicture =profilepicturedao.getname(userdetail.getLoginname());
		    if(profilepicture!=null) {
		    	return profilepicture.getImagestream();
		    }else {
		    	return null;
		    }
		   
		}

		
		
		
	}
	
}
