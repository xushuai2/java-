package demo.web.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import demo.Entity.FunctionEntity;
import demo.Entity.RoleEntity;
import demo.Entity.RoleFunctionMap;
import demo.Entity.UserSession;
import demo.common.Constant;
import demo.common.ExportExecl;
import demo.common.FunctionUtils;
import demo.common.ResponseUtil;
import demo.common.UserManager;
import demo.exception.ServiceException;
import demo.service.RoleService;

@Controller
@RequestMapping(value="/role")
public class RoleController extends BaseController {
	private static final Log logger = LogFactory.getLog(RoleController.class);
	@Resource(name="roleService")
	private RoleService roleService;
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
		return "xServer/role/list";
    }
	
	@ResponseBody
	@RequestMapping(value="/list",method = RequestMethod.POST)
    public Map<String,Object> list(String sort,String order,String name){
		List<RoleEntity> roles = null;
		try {
			//查出所有部门信息（全部不包括在内）
			roles = roleService.list(sort,order,name);
		} catch (ServiceException e) {
			logger.info(""+e);
			e.printStackTrace();
		}
		Map<String,Object> rst = new HashMap<String, Object>();
		List<Map<String,Object>> rowsData = new ArrayList<Map<String,Object>>();
		for(RoleEntity r : roles){
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("id", r.getId());
			data.put("name", r.getName());
			data.put("level", r.getLevel());
			data.put("remark", r.getRemark());
			rowsData.add(data);
		}
		rst.put("total",roles.size());
		rst.put("rows",rowsData);
		return rst;
    }
	@ResponseBody
	@RequestMapping(value="/getRoles",method = RequestMethod.POST)
    public List<Map<String,Object>> getRoles(){
		List<RoleEntity> roles = null;
		try {
			roles = roleService.list("id","asc",null);
		} catch (ServiceException e) {
			logger.info(""+e);
			e.printStackTrace();
		}
		List<Map<String,Object>> rowsData = new ArrayList<Map<String,Object>>();
		for(RoleEntity r :roles){
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("id", r.getId());
			data.put("name",r.getName());
			rowsData.add(data);
		}
		return rowsData;
    }
	@ResponseBody
	@RequestMapping(value="/getPermissions",method = RequestMethod.POST)
    public Map<String,Object> getPermissions(Integer id,Integer level,HttpServletRequest request){
		RoleEntity role = null;
		logger.info("==========>>>id="+id+"level="+level);
		if(id==null){
			logger.info("id==null");
			role = new RoleEntity();
			if(level!=0){
				role.setLevel(level);
			}else{			
				UserSession user = (UserSession) UserManager.getSessionInfo();
				role.setLevel(user.getLevel()+1);
			}
		}else{
			try {
				//查出角色信息（全部不包括在内）
				role = roleService.findById(id);
			} catch (ServiceException e) {
				logger.info(""+e);
				e.printStackTrace();
			}
		}
		List<FunctionEntity> functions = (List<FunctionEntity>)request.getSession().getServletContext().getAttribute(Constant.functions);		//所有的菜单
		Map<String,Object> rst =    new HashMap<String, Object>();
		List<Map<String,Object>> rowsData = FunctionUtils.getRoleFunctions(functions,role);
		rst.put("total",rowsData.size());
		rst.put("rows", rowsData);
		logger.info(rst);
		return rst;
    }
	
	@RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(Model model){	
		UserSession user=(UserSession) UserManager.getSessionInfo();
		model.addAttribute("level",user.getLevel());
		return "xServer/role/add";
    }
	@RequestMapping(value="/add",method = RequestMethod.POST)
	@ResponseBody
    public Map<String,Object> add(RoleEntity role,String ids[]){
		logger.info("权限:"+ids);
		if(ids == null){
			return ResponseUtil.sendResult(false,"新增失败,没有选择权限！");
		}
		List<RoleFunctionMap> roleFunctionmaps = new ArrayList<RoleFunctionMap>();
		logger.info("==========="+ids.length);
		
		for(int i=0;ids!=null && i<ids.length;i++){
			String[] purs = ids[i].split("=");
			if(purs.length!=2||purs[1].equals("0")){
				continue;
			}
			RoleFunctionMap roleFunctionmap = new RoleFunctionMap();
			roleFunctionmap.setRole(role);
			roleFunctionmap.setFkfunction(purs[0]);
			roleFunctionmap.setPurview(Integer.valueOf(purs[1]));
			roleFunctionmaps.add(roleFunctionmap);
		}
		role.setStatus(1);
		role.setRoleFunctionMaps(roleFunctionmaps);
		try {
			roleService.save(role);
		} catch (ServiceException e) {
			logger.info("解析出现异常"+e);
			return ResponseUtil.sendResult(false,e.getMessage());
		}
		return ResponseUtil.sendResult(true,"添加成功");
	}
	@RequestMapping(value = "/update",method = RequestMethod.GET)
    public String update(Integer id,Model model){
		RoleEntity role = null;
		try {
			role = roleService.findById(id);
			UserSession user=(UserSession) UserManager.getSessionInfo();
			model.addAttribute("level",user.getLevel());
		} catch (Exception e) {
			logger.info(""+e);
			e.printStackTrace();
		}
		model.addAttribute("role",role);
		return "xServer/role/update";
    }
	
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	@ResponseBody
    public  Map<String,Object> update(RoleEntity role,String ids[]){
		List<RoleFunctionMap> roleFunctionmaps = new ArrayList<RoleFunctionMap>();
		for(int i=0;ids!=null && i<ids.length;i++){
			String[] purs = ids[i].split("=");
			if(purs.length!=2||purs[1].equals("0")){
				continue;
			}
			RoleFunctionMap roleFunctionmap = new RoleFunctionMap();
			roleFunctionmap.setRole(role);
			roleFunctionmap.setFkfunction(purs[0]);
			roleFunctionmap.setPurview(Integer.valueOf(purs[1]));
			roleFunctionmaps.add(roleFunctionmap);
		}
		try {
			role.setRoleFunctionMaps(roleFunctionmaps);
			RoleEntity roleEntity=roleService.findById(role.getId());
			role.setStatus(roleEntity.getStatus());
			roleService.update(role);
		} catch (ServiceException e) {
			logger.info(""+e);
			return ResponseUtil.sendResult(false,e.getMessage());
		}
		return ResponseUtil.sendResult(true,"修改成功");
    }
	
	@RequestMapping(value = "/view",method = RequestMethod.GET)
    public String view(Integer id,Model model){
		RoleEntity role = null;
	
		try {
			//查出角色信息（全部不包括在内）
			role = roleService.findById(id);
		} catch (ServiceException e) {
			logger.info(""+e);
			e.printStackTrace();
		}
		model.addAttribute("role",role);
		return "xServer/role/view";
    }
	
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
    public  Map<String,Object> delete(HttpServletRequest request,RoleEntity role){
		try {
			logger.info("要删除的角色ID"+role.getId());
			RoleEntity roleEntity=roleService.findById(role.getId());
			roleService.delete(roleEntity);
		} catch (ServiceException e) {
			logger.info(""+e);
			return ResponseUtil.sendResult(false,e.getMessage());
		}
		return ResponseUtil.sendResult(true,"删除成功");
    }
	
	@ResponseBody
	@RequestMapping(value="/exportExcel",method = RequestMethod.POST)
    public void exportExcel(HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> datas = list("id","asc",null);
		List<Map<String, Object>> lists = (List<Map<String, Object>>) datas.get("rows");
		//title 表头  列名
//		String title = "\"角色名称\"\t\"角色类型\"\t\"备注\"\r\n";
		String[] title = {"角色名称","角色类型","备注"};
		//keys map的键名，同时按照显示的顺序排列     需和列名一致
		String[] keys = new String[]{"name","level","remark"};
		try {
			//文件名称
			String name = "Role ";		
			
			Map<String, Map<Integer,String>> map = new HashMap<String, Map<Integer,String>>();
			Map<Integer, String> m1=new HashMap<Integer, String>();
			m1.put(0,"超级管理员");
			m1.put(1,"系统管理员");
			m1.put(2, "部门管理员");
			m1.put(3, "普通用户");
			
			map.put("level", m1);
			
			logger.info("添加数据完成，开始导出");
			ExportExecl.downloadFile(ExportExecl.Expor(lists , keys, title , map , name),response);
		} catch (Exception e2) {
			logger.info(""+e2);
			e2.printStackTrace();
		}
    }
}
