package demo.web.controller;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import demo.exception.ServiceException;

public class BaseController  {


	/**
	  * getExceptionMessage(获取Service层的国际化异常)
	  * @param request
	  * @param exception service层抛出的异常
	  * @return
	 */
	protected String getExceptionMessage(HttpServletRequest request,ServiceException exception){  
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		return applicationContext.getMessage(exception.getMessage(),exception.getArgs().size()>0?exception.getArgs().toArray():null,exception.getMessage(),locale);  
	}
	
	
	/**
	  * getMessage(获取国际化内容)
	  * @param request 请求 必须要,因为在这里才有Local
	  * @param key 国际化的key
	  * @param args 参数数组
	  * @return
	 */
	protected String getMessage(HttpServletRequest request,String key,Object[] args){
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		return applicationContext.getMessage(key,args,key,locale); 
	}
}
