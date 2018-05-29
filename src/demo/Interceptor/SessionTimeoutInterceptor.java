package demo.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import demo.Entity.TUser;

/** 
 * 处理session超时的拦截器 
 */ 
public class SessionTimeoutInterceptor implements HandlerInterceptor{
	public String[] allowUrls;//还没发现可以直接配置不拦截的资源，所以在代码里面来排除  
    
    public void setAllowUrls(String[] allowUrls) {  
        this.allowUrls = allowUrls;  
    }  
  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,  
            Object arg2) throws Exception {  
        String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");    
        System.out.println(requestUrl);  
        if(null != allowUrls && allowUrls.length>=1)  
            for(String url : allowUrls) {    
                if(requestUrl.contains(url)) {    
                    return true;    
                }    
            }  
          
        TUser user = (TUser) request.getSession().getAttribute("user");  
        if(user != null) {    
            return true;  //返回true，则这个方面调用后会接着调用postHandle(),  afterCompletion()  
        }else{  
            // 未登录  跳转到登录页面  
        	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
			response.sendRedirect(basePath+"/user/index");
        }
		return true;  
    }
    
    
    
    /*postHandle(WebRequest request, ModelMap model) 方法。
        该方法将在请求处理之后，也就是在Controller 方法调用之后被调用，但是会在视图返回被渲染之前被调用，
        所以可以在这个方法里面通过改变数据模型ModelMap 来改变数据的展示。该方法有两个参数，WebRequest 对象是用于传递整个请求数据的，
        比如在preHandle 中准备的数据都可以通过WebRequest 来传递和访问；ModelMap 就是Controller 处理之后返回的Model 对象，
        我们可以通过改变它的属性来改变返回的Model 模型。*/
    
    @Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}  
    
    
    /*afterCompletion(WebRequest request, Exception ex) 方法。
        该方法会在整个请求处理完成，也就是在视图返回并被渲染之后执行。所以在该方法中可以进行资源的释放操作。*/
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		arg0.getSession().setAttribute("user", null);
	}

	
}
