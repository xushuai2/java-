package demo.web.controller;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie; 

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.Entity.FunctionEntity;
import demo.Entity.SessionInfo;
import demo.Entity.UserEntity;
import demo.Entity.UserSession;
import demo.common.Constant;
import demo.common.FunctionUtils;
import demo.common.IPUtils;
import demo.common.ResponseUtil;
import demo.common.UserManager;
import demo.service.UserService;
@Controller
public class MainController{
	private static final Log logger = LogFactory.getLog(MainController.class);
	@Resource(name="iuserService")
	private UserService iuserService;
	
	@RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
		logger.info("测试号页面");
		return "xServer/login";
    }
	
	
	@ResponseBody
	@RequestMapping(value="/login",method=RequestMethod.POST)
    public Map<String,Object> login(HttpServletRequest request,HttpServletResponse response,String userid,String password){
		//开始登录
		boolean login = true;
		String message = new String("");
		try {
			SessionInfo user = iuserService.login(request,userid, password);

			logger.info("-----cookie已经写入客户端---D:/CookieJavajsyj/MakeCookie---用户名:"+userid+" 登录success-");
			request.getSession().setAttribute(Constant.sessioninfo, user);
			message = "登录成功";
		} catch (Exception e) {
			logger.info("用户名:"+userid+" 登录异常  "+e);
			login = false;
			message = "登录失败";
		}
		return ResponseUtil.sendResult(login,message);
    }
	
	
	@ResponseBody
	@RequestMapping(value="/unlockwin",method=RequestMethod.POST)
    public Map<String,Object> unlockwin(HttpServletRequest request,String lockuid,String luckpwd){
		boolean login = true;
		String message = new String("");
		try {
			SessionInfo user = iuserService.login(request,lockuid, luckpwd);
			logger.info("----用户名:"+lockuid+" 解锁后台web   success-");
			request.getSession().setAttribute(Constant.sessioninfo, user);
			message = "解锁成功";
		} catch (Exception e) {
			logger.info("用户名:"+lockuid+"  解锁后台web  异常  "+e);
			message = "解锁失败";
			login = false;
		}
		return ResponseUtil.sendResult(login,message);
    }
	
	
	
	
	@RequestMapping(value = "/loginUpdatePwd",method = RequestMethod.GET)
    public String loginUpdatePwd(){
		logger.info("密码过期，修改密码页面");
		return "xServer/updateByPwd";
    }
	
	
	/*
	 * 徐帅  20150930
	 * 密码过期     修改密码
	 * */
	@ResponseBody
	@RequestMapping(value="/loginUpdatePwd",method=RequestMethod.POST)
    public Map<String,Object> loginUpdatePwd(HttpServletRequest request,String userid,String password,String newpassword){
		//开始登录
		boolean loginUpdatePwd = true;
		String message = new String("");
		@SuppressWarnings("unused")
		UserEntity user = null;
		try {
			user = iuserService.updatePwd(request,userid, password,newpassword);
			message = "用户名:"+userid+"  修改密码成功！";
		} catch (Exception e) {
			logger.info("用户名:"+userid+"  修改密码异常： "+e);
			loginUpdatePwd = false;
		}
		return ResponseUtil.sendResult(loginUpdatePwd,message);
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/main")
    public String main(HttpServletRequest request,Model model){
		List<FunctionEntity> listFunctions = (List<FunctionEntity>)request.getSession().getServletContext().getAttribute(Constant.functions);
		SessionInfo user = UserManager.getSessionInfo();
		listFunctions = FunctionUtils.getRoleMenus(listFunctions,user.getPermission());
		model.addAttribute("menus",listFunctions);
		return "xServer/main";
    }
	
	
	//查询cookie   运用cookie
	@RequestMapping(value = "/welcome")
    public String welcome(HttpServletRequest request,HttpServletResponse response,Model model){
		logger.info("----------------url:welcome---------start--------");
		String ip = "";
		String lasttime = "";
		UserSession user = (UserSession) UserManager.getSessionInfo();
		//Cookie cookies[] = ServletActionContext.getRequest().getCookies();
		Cookie [] cookies = request.getCookies();  
        for(int i=0;cookies!=null && i<cookies.length;i++){  
            if(cookies[i].getName().equals("LastAccessName")){  
                long time  = Long.valueOf(cookies[i].getValue());  
                Date date = new Date (time);  
                lasttime = date.toLocaleString();
            }  
        }  
        
        /*要想在cookie中存储中文，那么必须使用URLEncoder类里面的encode(String s, String enc)方法进行中文转码，
         * 例如：
        1 Cookie cookie = new Cookie("userName", URLEncoder.encode("孤傲苍狼", "UTF-8"));
        2 response.addCookie(cookie);
        　　   在获取cookie中的中文数据时，再使用URLDecoder类里面的decode(String s, String enc)进行解码，例如：
        1 URLDecoder.decode(cookies[i].getValue(), "UTF-8")
        */
        Cookie cok = new Cookie("LastAccessName",System.currentTimeMillis()+"");  
        cok.setMaxAge(3*60*1000);  // 设置为3分钟
        /*setPath此处的参数，是相对于应用服务器存放应用的文件夹的根目录而言的(比如tomcat下面的webapp)，因此cookie.setPath("/");之后，
                   可以在webapp文件夹下的所有应用共享cookie，而cookie.setPath("/webapp_b/");*/
        cok.setPath("/");  
        response.addCookie(cok); 
        //ServletActionContext.getResponse().addCookie(cookie);
		
		ip = IPUtils.getIpAddr(request);
		SimpleDateFormat timeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date welcomedate = new Date();
		String welcometime = timeFormat.format(welcomedate);
		model.addAttribute("welcometime",welcometime);
		model.addAttribute("branchname",user.getBranchname());
		model.addAttribute("username",user.getUsername());
		model.addAttribute("userid",user.getUserid());
		model.addAttribute("level",user.getRolename());
		model.addAttribute("ip",ip);
		model.addAttribute("LastAccessName",lasttime);
		logger.info(welcometime+"--welcome-"+user.getBranchname()+"--"+user.getUsername()+"--"+user.getUserid()+"--"+user.getRolename()+"--"+ip);
		return "xServer/welcome";
    }
	
	@RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request){
		try {
			iuserService.logout();
		} catch (Exception e) {
			logger.info(""+e);
			e.printStackTrace();
		}
		request.getSession().setAttribute(Constant.sessioninfo,null);
		return "redirect:login";
    }
	
	
	@ResponseBody
	@RequestMapping(value="/logout",method=RequestMethod.POST)
    public Map<String,Object> logout(HttpServletRequest request,Model model){
		try {
			iuserService.logout();
		} catch (Exception e) {
			logger.info(""+e);
			e.printStackTrace();
		}
		request.getSession().setAttribute(Constant.sessioninfo,null);
		return ResponseUtil.sendResult(true,"注销完成");
    }
}
