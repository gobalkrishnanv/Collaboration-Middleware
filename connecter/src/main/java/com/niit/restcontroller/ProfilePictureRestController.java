package com.niit.restcontroller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.niit.oracle.DAO.ProfilePictureDAO;
import com.niit.oracle.model.ProfilePicture;
import com.niit.oracle.model.UserDetail;


@RestController
public class ProfilePictureRestController
{
@Autowired
ProfilePictureDAO profilePictureDAO;



@PostMapping("/doUpload")
public ResponseEntity<?> uploadProfilePicture(@RequestParam(value="file") CommonsMultipartFile fileUpload, HttpSession session)
{
	UserDetail userDetail=(UserDetail)session.getAttribute("userDetail");
	if(userDetail==null)
	{
		return new ResponseEntity<String>("Unauthorised User",HttpStatus.NOT_FOUND);
	}
	else
	{
		
    String fa=userDetail.getLoginname()+".jpg";
		System.out.println(fa);
		File filepath=new File("E:\\workspace\\Frontend\\WebContent\\image\\"+fa);
        
		try {
			FileCopyUtils.copy(fileUpload.getBytes(),filepath);
			byte[] buffer=fileUpload.getBytes();
			FileOutputStream fos=new FileOutputStream(filepath);
			BufferedOutputStream bs=new BufferedOutputStream(fos);
			bs.write(buffer);
			bs.close();
			Robot r=new Robot();
			r.keyPress(KeyEvent.VK_F5);
		
			ProfilePicture p=new ProfilePicture();
			p.setLoginname(userDetail.getLoginname());
		    p.setImage(new SerialBlob(fileUpload.getBytes()));	
		    profilePictureDAO.add(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SerialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}

@GetMapping("/getImage/{username}")
public @ResponseBody byte[] getProfilePicture(@PathVariable("username") String loginname,HttpSession session)
{
	UserDetail userDetail=(UserDetail)session.getAttribute("userDetail");
	
	if(userDetail==null)
	{
		return null;
	}
	else
	{
	ProfilePicture profilePicture = profilePictureDAO.getname(loginname);
	
	if(profilePicture!= null)
	{
		SerialBlob bl=(SerialBlob) profilePicture.getImage();
		byte[] byt = null;
		try {
			
			byt= bl.getBytes(1,(int)bl.length());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return byt;
	}
	else
	{
		return null;
	}
}
}
}
