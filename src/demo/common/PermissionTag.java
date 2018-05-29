package demo.common;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import demo.Entity.SessionInfo;
public class PermissionTag extends BodyTagSupport {
	private static final long serialVersionUID = -332406617493607665L;
	private static final Log logger = LogFactory.getLog(PermissionTag.class);
	private String function;
	private int permission = 0;
	
	public int doStartTag() throws JspTagException {   
		//EVAL_BODY_BUFFERED：申请缓冲区，由setBodyContent()函数得到的BodyContent对象来处理tag的body，如果类实现了BodyTag，那么doStartTag()可用，否则非法
	    return EVAL_BODY_BUFFERED;
	}
	public int doEndTag() throws JspTagException {
		int purview = hasPermission();
	    if(purview>0 && purview>=permission){
	    	String body = this.getBodyContent().getString();
		    body = body.replace("$function", function);
		    body = body.replace("$purview",Integer.toString(purview));
	    	try {
	  	      pageContext.getOut().print(body);
	  	    }
	  	    catch (IOException e) {
	  	      throw new JspTagException(e.getMessage());
	  	    }
	    }
	    //EVAL_BODY_INCLUDE代表执行自定义标签中的内容，SKIP_BODY代表不执行自定义标签中的内容。  
	    return SKIP_BODY;
	}
	protected int hasPermission(){
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		HashMap<String,String> mapUrlKey = (HashMap<String,String>)pageContext.getServletContext().getAttribute(Constant.actions);
		if(mapUrlKey==null){
			logger.fatal("系统严重错误,获取权限对照表失败");
			return Constant.PERMISSION_NO;
		}
		
		
		//将权限的前边判断是否为/如果不是加上/
		if(function.length()>1 && !function.startsWith("/")){
			function = "/" + function;
		}
		
		String urlKey = mapUrlKey.get(function);
		if(urlKey==null){
			return Constant.PERMISSION_BRANCH;
		}
		
		//需要进行权限判断
		SessionInfo sessionInfo = (SessionInfo)request.getSession().getAttribute(Constant.sessioninfo);
		if(sessionInfo==null){
			//没找到用户信息 认为没权限
			logger.fatal("获取用户session信息为null");
			return Constant.PERMISSION_NO;
		}
		
		Integer permission = sessionInfo.getPermission().get(urlKey);
		if(permission==null){
			return Constant.PERMISSION_NO;
		}else if(permission.compareTo(1)==0){
			return Constant.PERMISSION_SELF;
		}else if(permission.compareTo(2)==0){
			return Constant.PERMISSION_BRANCH;
		}else if(permission.compareTo(3)==0){
			return Constant.PERMISSION_BRANCHANDCHILDREN;
		}
		return Constant.PERMISSION_NO;
	}
	
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public int getPermission() {
		return permission;
	}
	public void setPermission(int permission) {
		this.permission = permission;
	}
}
