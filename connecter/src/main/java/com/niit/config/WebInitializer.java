package com.niit.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.niit.oracle.config.ApplicationContext;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

	@Override
	protected Class<?>[] getRootConfigClasses() 
	{
		return new Class[] {WebResolver.class,ApplicationContext.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() 
	{
		return null;
	}

	@Override
	protected String[] getServletMappings() 
	{
		return new String[] {"/"};
	}
	


}
