package com.niit.restcontroller;
import java.util.*;

import org.hibernate.mapping.Array;

import com.niit.oracle.model.Blog;
import com.niit.oracle.model.BlogComment;
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        
		Blog b1=new Blog();
		b1.setBlogid(1);
		b1.setBlogname("b1");
		b1.setBlogcontent("b1");
		
		Blog b2=new Blog();
		b2.setBlogid(2);
		b2.setBlogname("b2");
		b2.setBlogcontent("b2");
		
		
		Blog b3=new Blog();
		b3.setBlogid(3);
		b3.setBlogname("b3");
		b3.setBlogcontent("b3");
		List<Blog> bl=new ArrayList<Blog>();
		bl.add(b1);
	    bl.add(b2);
	    bl.add(b3);
			
        BlogComment c1=new BlogComment();
        c1.setCommentid(1);
        c1.setBlogid(1);
        c1.setLoginname("c1");
        c1.setCommenttext("c1");
        c1.setCommentdate(new Date());
        
        BlogComment c2=new BlogComment();
        c2.setCommentid(2);
        c2.setBlogid(1);
        c2.setLoginname("c2");
        c2.setCommenttext("c2");
        c2.setCommentdate(new Date());
        
        
        
        
        BlogComment c3=new BlogComment();
        c3.setCommentid(3);           
        c3.setBlogid(1);              
        c3.setLoginname("c3");        
        c3.setCommenttext("c3");      
        c3.setCommentdate(new Date());
        
        BlogComment c4=new BlogComment();
        c4.setCommentid(4);        
        c4.setBlogid(2);               
        c4.setLoginname("c4");         
        c4.setCommenttext("c4");       
        c4.setCommentdate(new Date()); 
        
        BlogComment c5=new BlogComment();
         
        c5.setCommentid(5);            
        c5.setBlogid(3);               
        c5.setLoginname("c5");         
        c5.setCommenttext("c5");       
        c5.setCommentdate(new Date()); 
        
        BlogComment c6=new BlogComment();
        
        c6.setCommentid(5);            
        c6.setBlogid(2);               
        c6.setLoginname("c6");         
        c6.setCommenttext("c6");       
        c6.setCommentdate(new Date());
        
BlogComment c7=new BlogComment();
        
        c7.setCommentid(5);            
        c7.setBlogid(3);               
        c7.setLoginname("c7");         
        c7.setCommenttext("c7");       
        c7.setCommentdate(new Date()); 
        
        
        List<BlogComment> l=new ArrayList<BlogComment>();
        l.add(c1);
        l.add(c2);
        l.add(c3);
        l.add(c4);
        l.add(c5);
        l.add(c6);
        l.add(c7);
        Concurrent c=new Concurrent();
      System.out.println(c.list(bl,l));
        
     	}
	
}
