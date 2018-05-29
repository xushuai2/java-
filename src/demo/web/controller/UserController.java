package demo.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.Entity.BranchEntity;
import demo.Entity.UserEntity;
import demo.Entity.UserSession;
import demo.common.Constant;
import demo.common.EncryptUtil;
import demo.common.ExportExecl;
import demo.common.PageData;
import demo.common.ResponseUtil;
import demo.common.UserManager;
import demo.exception.ServiceException;
import demo.service.BranchService;
import demo.service.UserService;

@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController {
	private static final Log logger = LogFactory.getLog(UserController.class);
	@Resource(name="iuserService")
	private UserService userService;
	@Resource(name="branchService")
	private BranchService branchService;
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
		return "xServer/user/list";
    }
	
	
	@ResponseBody
	@RequestMapping(value="/uidlist",method = RequestMethod.POST)
	public List<UserEntity>  uidlist(){
		List<UserEntity> users = null;
		try {
			users = userService.Useridlist();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/list",method = RequestMethod.POST)
    public Map<String,Object> list(@RequestParam(value="rows",defaultValue="999999999") int rows,
    		 @RequestParam(value="page",defaultValue="0") int page,
    		 @RequestParam(value="sort") String sort,
    		 @RequestParam(value="order") String order,
    		 Integer branchid,
    		 String userid){
		PageData<UserEntity> users = null;
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm");
		UserSession user = (UserSession)UserManager.getSessionInfo();
		try {
			//查出所有角色信息（全部不包括在内）
			Integer lft=0,rgt=0;
			if(branchid!=null){
				BranchEntity bran=branchService.findById(branchid);
				lft=bran.getLft();
				rgt=bran.getRgt();
			}else{
				lft=user.getLft();
				rgt=user.getRgt();
			}
			users = userService.list(rows,page,sort,order,lft,rgt,branchid,userid);
		} catch (ServiceException e) {
			logger.info(""+e);
			e.printStackTrace();
		}
		Map<String,Object> rst = new HashMap<String, Object>();
		List<Map<String,Object>> rowsData = new ArrayList<Map<String,Object>>();
		for(UserEntity u : users.getDataList()){
			if(u.getId()==user.getUid())continue;
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("id", u.getId());
			data.put("userid", u.getUserid());
			data.put("name", u.getName());
			data.put("role", u.getRole().getName());
			data.put("branch", u.getBranch().getName());
			data.put("status", u.getStatus());
			data.put("remark", u.getRemark());
			data.put("lltime", u.getLltime()!=null?u.getLltime():"");
			data.put("llipaddr", u.getLlipaddr());
			rowsData.add(data);
		}
		rst.put("total",users.getTotalcount());
		rst.put("rows",rowsData);
		return rst;
    }
	@RequestMapping(value = "/changepassword",method = RequestMethod.GET)
    public String changepassword(){
		return "xServer/user/changepassword";
    }
	@ResponseBody
	@RequestMapping(value="/changepassword",method = RequestMethod.POST)
    public Map<String,Object> changepassword(String oldpwd,String newpwd){
		UserSession usersession = (UserSession)UserManager.getSessionInfo();
		logger.info(newpwd);
		try {
			UserEntity user = userService.findById(usersession.getUid());
			if(!EncryptUtil.authenticate(oldpwd, user.getPassword(), user.getSalt())){
				return ResponseUtil.sendResult(false,"修改失败，密码错误");
			}
			if(newpwd.length()<5 || newpwd.length()>20){
				return ResponseUtil.sendResult(false,"修改失败，新密码长度应在5-20");
			}
			user.setPassword(newpwd);
			userService.update(user);
		} catch (ServiceException e) {
			logger.info(""+e);
			e.printStackTrace();
		}
		
		return ResponseUtil.sendResult(true,"修改密码成功");
	}
	/**
	 * *******************
	* @return
	* 2014-8-26
	* @author:MichaelSun
	* *******************
	 */
	@RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(){
		return "xServer/user/add";
    }
	@ResponseBody
	@RequestMapping(value="/add",method = RequestMethod.POST)
    public Map<String,Object> add(UserEntity user){
		
		try {
			//查出所有部门信息（全部不包括在内）
			userService.save(user);
		} catch (ServiceException e) {
			logger.info(""+e);
			return ResponseUtil.sendResult(false,e.getMessage());
		}
		return ResponseUtil.sendResult(true,"添加成功");
	}
	@RequestMapping(value = "/update",method = RequestMethod.GET)
    public String update(Integer id,Model model){
		try {
			UserEntity user = userService.findById(id);
			model.addAttribute("user",user);
		} catch (ServiceException e) {
			logger.info(""+e);
			e.printStackTrace();
		}
		return "xServer/user/update";
    }
	@ResponseBody
	@RequestMapping(value = "/update")
    public Map<String,Object> update(UserEntity user){
		try {
			userService.update(user);
		} catch (ServiceException e) {
			logger.info(""+e);
			return ResponseUtil.sendResult(false,"修改失败"+e.getMessage());
		}
		return ResponseUtil.sendResult(true,"修改成功");
    }
	
	
	@RequestMapping(value = "/view",method = RequestMethod.GET)
    public String view(Integer id,Model model){
		UserEntity user = null;
	
		try {
			//查出角色信息（全部不包括在内）
			user = userService.findById(id);
		} catch (ServiceException e) {
			logger.info(""+e);
			e.printStackTrace();
		}
		model.addAttribute("user",user);
		return "xServer/user/view";
    }
	
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
    public  Map<String,Object> delete(HttpServletRequest request,UserEntity user){
		try {
			logger.info("要删除的用户ID"+user.getId());
			UserEntity userEntity=userService.findById(user.getId());
			if(userEntity!=null && userEntity.getUserid().equals("000000")){
				return ResponseUtil.sendResult(false,"编号是000000的用户不能删除！");
			}
			userService.delete(userEntity);
		} catch (ServiceException e) {
			logger.info(""+e);
			return ResponseUtil.sendResult(false,getExceptionMessage(request,e));
		}
		return ResponseUtil.sendResult(true,"删除成功");
    }
	
	/**
	 * userInfo(个人信息窗口)
	 * @return userInfo
	 */
	@RequestMapping(value = "/userInfo")
    public String  showUserInfo(Model model){
		return "xServer/user/userInfo";
	}
	
	@ResponseBody
	@RequestMapping(value = "/findByuserid",method = RequestMethod.POST)
	public Map<String,Object> findByuserid(HttpServletRequest request,String userno){
		Map<String, Object> rowmap=new HashMap<String, Object>();
		logger.info("======>查询的柜员的编号："+userno);
		String message = null;
		if("".equals(userno)) {
			message = "柜员号不能为空,请输入柜员号";
			rowmap.put("yesorno",false);
			rowmap.put("bewrite", message);
			return rowmap;
		}
		UserEntity user=null;
		try {
			user = userService.findByUserid(userno);
		}catch (ServiceException e) {
			logger.info(""+e);
			message = "查询失败";
			rowmap.put("yesorno",false);
			rowmap.put("bewrite", message);
			return rowmap;
		}
		if(user!=null){
			if(user.getStatus() == 1){
				message = "该柜员信息正常，可以开卡";
				rowmap.put("yesorno",true);
				rowmap.put("bewrite", message);
				rowmap.put("id",user.getId());
				rowmap.put("host",user.getName());
				return rowmap;
			}else{
				message = "该柜员信息异常，不可以绑定";
				rowmap.put("yesorno",false);
				rowmap.put("bewrite", message);
				return rowmap;
			}
		}else{
			message = "该柜员不存在,不可以使用";
			rowmap.put("yesorno",false);
			rowmap.put("bewrite", message);
			return rowmap;
		}
	}
	
	/**
	 * toLockWin(锁定页面窗口)
	 */
	@RequestMapping(value = "/toLockWin")
    public ModelAndView toLock(HttpServletRequest request){
		logger.info("toLock锁定窗口方法开始指行");
		UserSession usersession = (UserSession)UserManager.getSessionInfo();
		String uid = usersession.getUserid();
		UserManager.setSessionInfo(null);
		request.getSession().setAttribute(Constant.sessioninfo,null);
		ModelAndView mv = new ModelAndView("xServer/user/lockwin");
		
		mv.addObject("uid", uid);
		return mv;
	}
	@RequestMapping(value = "/toexport" , method = RequestMethod.GET)
	public ModelAndView toexport(HttpServletRequest request,int rows,
    		int page,String sort,String order,Integer branchid,String userid,Model model){
		logger.info("导出   调用查询客户卡 的方法:rows="+rows+",page="+page+",sort="+sort+",order="+order+",branchid="+branchid+",userid="+userid);
		ModelAndView mv = new ModelAndView("xServer/user/export");
		model.addAttribute("rows",rows);
		model.addAttribute("page",page);
		model.addAttribute("sort",sort);
		model.addAttribute("order",order);
		model.addAttribute("branchid",branchid);
		model.addAttribute("userid",userid);
		return mv;
	}
	@ResponseBody
	@RequestMapping(value="/exportExcel",method = RequestMethod.POST)
    public void exportExcel(HttpServletRequest request,int rows,int page,String sort,String order,
    		Integer branchid,String userid,int exporttype,HttpServletResponse response){
		List<Map<String, Object>> lists = null;
		logger.info("导出   调用查询客户卡 的方法:rows="+rows+",page="+page+",sort="+sort+",order="+order+",fkbranch="+branchid+"+,userid="+userid+",type="+exporttype);
		
		if(exporttype == 1){
			rows = 999999999;
			page = 1;
		}
		Map<String, Object> datas = list(rows,page,sort,order,branchid,userid);
		lists = (List<Map<String, Object>>) datas.get("rows");
		Map<String,Object> rst = new HashMap<String, Object>();
		List<String> rowsData = new ArrayList<String>();
		//title 表头  列名
//		String title = "\"用户编号\"\t\"用户名称\"\t\"角色\"\t\"部门\"\t\"状态\"\t\"最后登录时间\"\t\"最后登录IP\"\t\"备注\"\r\n";
		String[] title = {"用户编号","用户名称","角色","部门","状态","最后登录时间","最后登录IP","备注"};
		//keys map的键名，同时按照显示的顺序排列     需和列名一致
		String[] keys = new String[]{"userid","name","role","branch","status","lltime","llipaddr","remark"};
		try {
			//文件名称
			String name = "User";		
			if(branchid !=null ){
				name += "-b"+branchid;
			}
			if(userid != null && userid.length() > 0){
				name += "-u"+userid;
			}
			//需要进行翻译的字段
			Map<String, Map<Integer, String>> map = new HashMap<String, Map<Integer,String>>();
			Map<Integer,String> m1 = new HashMap<Integer, String>();
			m1.put(1,"正常");
			m1.put(2,"锁定");
			m1.put(9,"禁用");
			map.put("status", m1);
			
			logger.info("添加数据完成，开始导出");
			ExportExecl.downloadFile(ExportExecl.Expor(lists , keys, title , map , name),response);
		} catch (Exception e2) {
			logger.info(""+e2);
			e2.printStackTrace();
		}
    }
}
