package com.niit.restcontroller;

import java.util.ArrayList;
import java.util.List;

import com.niit.oracle.model.Blog;
import com.niit.oracle.model.BlogComment;

public class Concurrent {
	
	
	
	
	
	
	public static List<BlogComment> sort(List<BlogComment> l){
	
	
	for(int j=0;j<l.size()-1;j++) {
		for(int i=0;i<l.size()-1;i++) {
			
			if(l.get(i).getBlogid()>l.get(i+1).getBlogid()) {
				BlogComment temp=l.get(i);
				l.set(i, l.get(i+1));
				l.set(i+1, temp);
			}
		}	
	}
	
	return l;
	
	
}
	
	public static List<Blog> sortblog(List<Blog> l){
		
		
		for(int j=0;j<l.size()-1;j++) {
			for(int i=0;i<l.size()-1;i++) {
				
				if(l.get(i).getBlogid()>l.get(i+1).getBlogid()) {
					Blog temp=l.get(i);
					l.set(i, l.get(i+1));
					l.set(i+1, temp);
				}
			}	
		}
		
		return l;
		
		
	}
	
public static List<BlogArrange> list(List<Blog> bl, List<BlogComment> l){
	sort(l);
    int k=0,v=0,c=0;
    boolean cc=false;
    List<Integer> csi=new ArrayList<Integer>();
    csi.removeAll(csi);
	
    for(int i=0;i<l.size();i++) {
    	csi.add(l.get(i).getBlogid());
    }
    
     
    	int co=0;
    	for(int i=0;i<l.size();i++) {
    	
    		
    	 k=csi.get(i);
        if(i==l.size()-1) {v=0;}else { 
    	v=csi.get(i+1);}
   
          

        if(k==v) {
        	csi.set(i, co);
        	co++;   
         
        }else {
        	csi.set(i, co)	;
        	co=0;

        	c++;
        } 
    }

    
   // System.out.println(csi);
    
    
    boolean cv=false;
    for(int i=0;i<csi.size();i++) {
    	int x=csi.get(i);
        int y=0;
    	 if(i==csi.size()-1) {y=0;}else { 
         	y=csi.get(i+1);}
        
    	
    	        	
    	if(x==y) {
    		
    	}else {
            //System.out.println(i);

             if(y==0){
            	 
             } else {
            	 cv=false;
            	 }
    	}
    	


    }
    
	  
	   List<Integer> s1=new ArrayList<Integer>();
	    s1.removeAll(s1);
	    
        for(int i=0;i<csi.size();i=i+1) {
         //s.add(csi.get(i),l.get(i));

         int x=csi.get(i);
         int y=0;
         if(i==csi.size()-1) {y=0;}else {y=csi.get(i+1);}
         
         if(x>y) {
        	
         }
         else {
        	 csi.set(i, -1) ; 
        	 
           }
         
         
        }
         
       // System.out.println("------------------------------");
        
   
   //System.out.println(csi);
   List<Integer> s2=new ArrayList<Integer>(); 
   for(int i=0;i<csi.size();i++) {
	    if(csi.get(i)!=-1) {
	    	s2.add(csi.get(i));
	    }
   }
  int ca=0; 
  List<List<BlogComment>> csa=new ArrayList<List<BlogComment>>();
  
  for(int j=0;j<s2.size();j++) {
	  List<BlogComment> s=new ArrayList<BlogComment>();
	  s.removeAll(s);
	  for(int i=0;i<=s2.get(j);i++) {
				 
  	     s.add(l.get(ca));
			ca++;
		  } 
	  //System.out.println(s);
	    csa.add(s) ;
		System.out.println("------------------------------------------------------------------------------------------------------");
	 }
  
  List<BlogArrange> lc=new ArrayList<BlogArrange>();
  sortblog(bl);
  for(int i=0;i<bl.size();i++) {
	  BlogArrange b=new BlogArrange();
	  b.set(bl.get(i), csa.get(i));
	  lc.add(b);
  }
  
  
  
return lc;
}
}
