package com.niit.config;

import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.niit.oracle.config.ApplicationContext;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

	
	protected void customizeRegistration(ServletRegistration.Dynamic registration) {
		registration.setInitParameter("dispatchOptionsRequest", "true");
		registration.setAsyncSupported(true);
	}
	@Override
	protected Class<?>[] getRootConfigClasses() 
	{
		return new Class[] {WebResolver.class,ApplicationContext.class,WebSocketConfig.class};
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
	
    protected Filter[] getServletFilter()
    {
    	CharacterEncodingFilter encodingfilter=new CharacterEncodingFilter();
    	encodingfilter.setEncoding(StandardCharsets.UTF_8.name());
    	return new Filter[] {encodingfilter};
    	
    }

}
