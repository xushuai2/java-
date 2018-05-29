package demo.Interceptor;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import demo.Entity.SessionInfo;
import demo.common.Constant;
import demo.common.UserManager;
/**
* @ClassName: LoginAndPermissionInterceptor 
* @Description: 登录和权限拦截器
* @date 2014-8-4 下午3:35:18
 */
public class LoginAndPermissionInterceptor extends HandlerInterceptorAdapter implements ServletContextAware{
	private static final Log logger = LogFactory.getLog(LoginAndPermissionInterceptor.class);
	
	protected ServletContext servletContext;
	
	private HashMap<String,String> mapUrlKey = null;
	
	//白名单
	private String[] whiteListing =null;	
	
	private PathMatcher pathMatcher = new AntPathMatcher();
	
	@SuppressWarnings("unchecked")
	@Override
	public void setServletContext(ServletContext servletContext) {
		logger.info("**********************************************开始拦截器LoginAndPermissionInterceptor");
		this.servletContext = servletContext;
		//这里获取所有的 权限设置路径 只有在这里的路径才需要拦截
		mapUrlKey = (HashMap<String,String>)this.servletContext.getAttribute(Constant.actions);
	}
	
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception{
		logger.info("*******拦截路径:"+request.getServletPath());
		String url = request.getServletPath();
		SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute(Constant.sessioninfo);
		UserManager.setSessionInfo(sessionInfo);
		if(isWhiteListing(url)==true){
			//白名单
			logger.info("---------------white list");
			return true;
		}
		
		if(url.compareTo("/login")==0 ){
			//这里要判断有无session 有session进main 否则 login
			if(sessionInfo==null){
				return true;
			}else{
				String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
				//直接进主页面
				response.sendRedirect(basePath+"main");			
				return false;
			}
		}
		//logout 如果有session 则直接去退出,没有说明是超时了,直接跳转
		if(url.compareTo("/logout")==0 ){
			if(sessionInfo==null){
				String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
				response.sendRedirect(basePath+"login");			//直接进主页面
				return false;
			}else{
				return true;
			}
		}
		//下边开始权限判断
		if(sessionInfo==null){
			boolean ajaxRequest = isAjaxRequest(request);
			if(ajaxRequest){
				request.setAttribute("requestUrl",request.getRequestURL().toString());
				request.getRequestDispatcher("/WEB-INF/view/error/timeout.jsp").forward(request, response);	
				logger.info("返回超时");
				return false;
			}else{
				//其它页面直接返回login
				String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
				response.sendRedirect(basePath+"login");
				return false;
			}
		}else{
			String urlKey = mapUrlKey.get(url);
			if(urlKey!=null){		//不需要权限判断的直接放过去了
				//需要进行权限拦截 
				Integer permission = sessionInfo.getPermission().get(urlKey);
				if(permission!=null &&permission.intValue()>0){
					//需要在这注入一下权限,还有一些参数
					logger.info("有权限");
				}else{
					//走到这了其实是没权限
					//TODO 记录日志
					//TODO 在这要进行判断 是ajax请求还是页面请求 ajax请求返回无权限的json 页面则返回无权限页面
					logger.info("无权限");
					return true;
				}
			}
		}
		return true;		
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
		logger.info("afterCompletion url:"+request.getServletPath()+"Exception:"+ex==null?"":ex);
		UserManager.setSessionInfo(null);
	}
	
	
	/**
	 * isAjaxRequest(判断是否是ajax请求)
	 * @param request 请求
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {  
	    String header = request.getHeader("X-Requested-With");  
	    if (header != null && "XMLHttpRequest".equals(header)){
	    	return true;  
	    }else{
	    	return false;  
	    }
	}
	/**
	 * 判断一个url是否需要进行权限登录权限认证
	* @param url
	* @return
	* *******************
	 */
	protected boolean isWhiteListing(String url){
		boolean check = false;
		if(whiteListing!=null){
			for(int i=0;i<whiteListing.length;i++){
				if (this.pathMatcher.match(whiteListing[i],url)) {
					logger.info("白名单:"+url);
					check = true;
					break;
				}    
			}
		}
		return check;
	}
	
	
	public String[] getWhiteListing() {
		return whiteListing;
	}
	public void setWhiteListing(String[] whiteListing) {
		this.whiteListing = whiteListing;
	}

}
