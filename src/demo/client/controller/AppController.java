package demo.client.controller;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.Aop.OpLogger;
import demo.Aop.OpLogger.OpType;
import demo.Entity.AppStatistics;
import demo.Entity.Boss;
import demo.Entity.Car;
import demo.Entity.Dcyjmessage;
import demo.Entity.TUser;
import demo.common.DateUtils;
import demo.common.ResponseUtil;
import demo.service.IUserService;
/*@Controller 用于标注控制层组件（如srping mvc的controller,struts中的action）*/
@Controller
@RequestMapping("/app")
public class AppController{
	@Resource
	private IUserService userService;
	@Resource
	private Car car;
	@Resource
	private Boss boss;
	
	
	@Value("#{proConfig}")
	private Properties prop;
	
	private static final Log logger = LogFactory.getLog(AppController.class);
	
	private static Object obj = new Object();
	
	static ConcurrentHashMap<Integer,AtomicInteger> increaseCountMap= new ConcurrentHashMap<Integer,AtomicInteger>();
	
	protected void noHandlerFound(HttpServletRequest request,HttpServletResponse response) throws Exception {		
	    response.sendRedirect(request.getContextPath() + "/notFound");
	}
	
	
	      
	
	
	@RequestMapping("/detail")
	@OpLogger(id = "18611112222", type=OpType.SEARCH)  
	public String detail(HttpServletRequest request,Model model){
		System.out.println("pro-------------------------------"+prop.get("Weight"));
		/*int userId = Integer.parseInt(request.getParameter("id"));
		TUser user = this.userService.getUserById(userId);
		model.addAttribute("user", user);
		request.getSession().setMaxInactiveInterval(20);//20秒  
		request.getSession().setAttribute("user", user);  
		car.start();
		boss.start();*/
		String type = request.getParameter("type");
		List<AppStatistics> d  = userService.selectAPP(type);
		return "userdetail"; 
	}
	
	@RequestMapping(value="showImg")
    public void ShowImg(HttpServletRequest request,HttpServletResponse response) throws IOException{
       String imgFile = request.getParameter("imgFile"); //文件名
       String path= "D:/tempfile";//这里是存放图片的文件夹地址
       FileInputStream fileIs=null;
       try {
    	   fileIs = new FileInputStream(path+"/"+imgFile);
       } catch (Exception e) {
    	   logger.error("系统找不到图像文件："+path+"/"+imgFile);        
	       return;
       }
       int i=fileIs.available(); //得到文件大小   
       byte data[]=new byte[i];   
       fileIs.read(data);  //读数据   
       response.setContentType("image/*"); //设置返回的文件类型   
       //得到向客户端输出二进制数据的对象   
       OutputStream outStream=response.getOutputStream(); 
       outStream.write(data);  //输出数据      
       outStream.flush();  
       outStream.close();   
       fileIs.close();   
    }
	
	
	
	
	@RequestMapping(value = "/tjdc",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> submitdc(HttpServletRequest request,Dcyjmessage dcyjmessage){
		int num = 0;
		dcyjmessage.setDate(DateUtils.getStringDateShort("yyyy-MM-dd HH:mm:ss"));
		try {
			num = userService.saveDcyjmessage(dcyjmessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(num>0){
			return ResponseUtil.sendResult(true,"提交成功");
		}else{
			return ResponseUtil.sendResult(false,"提交失败！");
		}
	}
	
	@RequestMapping(value = "/updatedxc",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateDuoxiancheng(HttpServletRequest request,String id,String no){
		int num = 0;
		logger.info("updatedxc---线程："+no+"--id:"+id);
		try {
			int iduo = Integer.parseInt(id);
			num = userService.updatedxc(iduo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(num>0){
			return ResponseUtil.sendResult(true,"提交成功");
		}else{
			return ResponseUtil.sendResult(false,"提交失败！");
		}
	}
	
	
	
	@RequestMapping(value = "/zhuce",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> submitdc(HttpServletRequest request,TUser tuser){
		tuser.setZctime(DateUtils.getStringDateShort("yyyy-MM-dd HH:mm:ss"));
		tuser.setLevel("初级会员");
		tuser.setCredit(0);
		tuser.setStatus(0);
		int num = 0;
		try {
			num = userService.savetuser(tuser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(num>0){
			return ResponseUtil.sendResult(true,"提交成功");
		}else{
			return ResponseUtil.sendResult(false,"提交失败！");
		}
	}
	
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> login(HttpServletRequest request,TUser tuser){
		TUser tu = new TUser();
		try {
			tu = userService.findUser(tuser);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtil.sendResult(false,"提交失败！");
		}
		if(tu.getPassword()!=null&&tu.getPassword().equals(tuser.getPassword())){
			request.getSession().setAttribute("tu", tu);
			return ResponseUtil.sendResult(true,"提交成功");
		}else{
			return ResponseUtil.sendResult(false,"提交失败！");
		}
	}
	
	
	@RequestMapping(value = "/showBysearch",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object>  showBysearch(HttpServletRequest request){
	   ArrayList<String> list = new ArrayList<String>();
       list.add("value1");
       list.add("value2");
       list.add("value3");
       Map<String,Object> m = new HashMap<String,Object>();
       m.put("success", 1);
       m.put("list", list);
       return m;
	}
	
	@RequestMapping("/main")
	public String login(HttpServletRequest request,Model model) throws Exception{
		return "main"; 
	}
	
	@RequestMapping("/client")
	public String client(HttpServletRequest request,Model model) throws Exception{
		return "client"; 
	}
	
	@RequestMapping("/towjdc")
	public String towjdc(HttpServletRequest request,Model model) throws Exception{
		return "wjdc"; 
	}
	
	
	@RequestMapping("/layout")
	public String layout(HttpServletRequest request,Model model) throws Exception{
		request.getSession().setAttribute("tu",null);
		return "client"; 
	}
	
	@RequestMapping("/toShowphoto")
	public String toShowphoto(HttpServletRequest request,Model model) throws Exception{
		return "show1"; 
	}
	
	@RequestMapping("/toShow")
	public String toShow(HttpServletRequest request,Model model) throws Exception{
		return "show2"; 
	}
	
	@RequestMapping("/testServiceExcep")
	@OpLogger(id = "18611110000", type=OpType.SEARCH)  
	public String testServiceExcep(HttpServletRequest request,Model model) throws java.lang.Exception{
		this.userService.gettestUserById(1);
		return "userdetail"; 
	}
	
	
	@RequestMapping("/mongodbtest")
	public String mongodbtest(HttpServletRequest request,Model model) throws java.lang.Exception{
		TUser user = new TUser();
		user.setName("shijia");
		user.setPassword("888888");
		user.setStatus(0);
		this.userService.saveUser(user);;
		model.addAttribute("user", user);
		return "userdetail"; 
	}
	
	
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request,Model model) throws java.lang.Exception{
/*		int Id = Integer.parseInt(request.getParameter("id"));
		String implname  = Id ==1 ?"womenAmericaServiceImpl":"womenJapanServiceImpl";
		IwomenService iw = (IwomenService) SpringContextUtil.getBean(implname);
		System.out.println("老公name:"+iw.getMan("girl"));
		iw.jiehun("girl");*/
		System.out.println("---------------------转到index.jsp");
		return "index"; 
	}
	
	
	
	
	@RequestMapping("/null")  
    public void testNullPointerException() {  
		TUser blog = null;  
        //这里就会发生空指针异常，然后就会返回定义在SpringMVC配置文件中的null视图  
        System.out.println(blog.getId());  
    }  
      
    @RequestMapping("/number")  
    public void testNumberFormatException() {  
        //这里就会发生NumberFormatException，然后就会返回定义在SpringMVC配置文件中的number视图  
        Integer.parseInt("abc");  
    }  
      
    @RequestMapping("/default")  
    public void testDefaultException() {  
        if (1==1)  
            //由于该异常类型在SpringMVC的配置文件中没有指定，所以就会返回默认的exception视图  
            throw new RuntimeException("Error!");  
    }  
	
    
    
   
    @RequestMapping(value="/showCourseDetail",method=RequestMethod.GET)
    public ModelAndView showCourseDetail(HttpServletRequest request){
	    //其他内容忽略，至关注点击量的业务处理
	    String courseId = null;
	    synchronized(obj){//加锁防止并发
		    courseId = request.getParameter("courseId");
		    calClickRate(request,courseId );
	    }
	    return new ModelAndView("courseDetail");
    }

    private void  calClickRate(HttpServletRequest request,String courseIdStr){
	    Integer courseId = Integer.valueOf(courseIdStr);
	    increaseCountMap = (ConcurrentHashMap<Integer,AtomicInteger>)request.getSession().getServletContext().getAttribute("increaseCountMap");
	    Iterator it =increaseCountMap.entrySet().iterator();
	    while(it.hasNext()){
		    Map.Entry<Integer,AtomicInteger> me = (Map.Entry<Integer,AtomicInteger>)it.next();
		    if(me.getKey().intValue()== courseId.intValue()){
		    	increaseCountMap.putIfAbsent(courseId,new AtomicInteger(me.getValue().getAndIncrement()));
		    }else{
		    	increaseCountMap.putIfAbsent(courseId,new AtomicInteger(1));
		    }
	    }
	    if(!it.hasNext()){
	    	increaseCountMap.putIfAbsent(courseId,new AtomicInteger(1));
	    }
    	request.getSession().getServletContext().setAttribute("increaseCountMap",increaseCountMap);
    } 
    
}
