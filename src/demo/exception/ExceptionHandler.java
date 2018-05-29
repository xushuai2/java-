package demo.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class ExceptionHandler implements HandlerExceptionResolver {
	@Override  
    public ModelAndView resolveException(HttpServletRequest request,  
            HttpServletResponse response, Object handler, Exception ex) {  
		
        if (ex instanceof NumberFormatException) {  
        	request.setAttribute("ex", "转换类型失败："+ex);
            return new ModelAndView("error");  
        } else if (ex instanceof NullPointerException) {
        	request.setAttribute("ex", "空指针："+ex);
            return new ModelAndView("error");  
        }  
        request.setAttribute("ex", ex);
        return new ModelAndView("error");  
    }  
}
