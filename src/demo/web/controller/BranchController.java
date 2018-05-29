package demo.web.controller;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.Entity.BranchEntity;
import demo.Entity.UserSession;
import demo.common.BranchUtils;
import demo.common.Constant;
import demo.common.DateUtil;
import demo.common.ExportExecl;
import demo.common.ResponseUtil;
import demo.common.UserManager;
import demo.exception.ServiceException;
import demo.service.BranchService;

@Controller
@RequestMapping(value="/branch")
public class BranchController extends BaseController {
	private static final Log logger = LogFactory.getLog(BranchController.class);
	@Resource(name="branchService")
	private BranchService branchService;
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) throws Exception{
		//注册自定义的属性编辑器  
	    //1、日期  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
	    CustomDateEditor dateEditor = new CustomDateEditor(df, true);  
	    //表示如果命令对象有Date类型的属性，将使用该属性编辑器进行类型转换  
	    binder.registerCustomEditor(Date.class, dateEditor);
	}
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
		return "xServer/branch/list";
    }
	
	@ResponseBody
	@RequestMapping(value="/list",method = RequestMethod.POST)
    public Map<String,Object> list(String name){
		List<BranchEntity> branchs = null;
		try {
			//查出所有部门信息（全部不包括在内）
			branchs = branchService.list(null, null);
		} catch (ServiceException e) {
			logger.info(""+e);
			e.printStackTrace();
		}
		logger.info("共查询部门数据:"+branchs.size());
		Map<String,Object> rst = new HashMap<String, Object>();
		List<Map<String,Object>> rowsData = new ArrayList<Map<String,Object>>();
		for(BranchEntity b : branchs){
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("id", b.getId());
			data.put("branchid", b.getBranchid());
			data.put("name", b.getName());
			data.put("parentbranchid",b.getParent()==null?"":b.getParent().getBranchid());
			data.put("parentname",b.getParent()==null?"":b.getParent().getName());
			data.put("remark",b.getRemark());
			//如果此部门存在父部门并且父部门pkid存在于branch表中，将此部门加入到data中（根部门pkid固定为1）
			if(b.getParent()!=null && b.getParent().getId()!=0 && parentInList(branchs,b.getParent().getId())){
				data.put("_parentId",b.getParent().getId());
			}
			rowsData.add(data);
		}
		rst.put("total",branchs.size());
		rst.put("rows",rowsData);
		
		return rst;
    }
	
	
	/**判断父部门pkid是否存在于branch表中 */
	private boolean parentInList(List<BranchEntity> branchs,int parent){
		for(BranchEntity b : branchs){
			if(parent == b.getId())
				return true;
		}
		return false;
	}
	
	
	
	/**
	 * getBranchs(下拉菜单显示部门信息）
	 * @param request
	 * @param exceptions 
	 * @return List
	 * @throws ServiceException
	 */
	@ResponseBody
	@RequestMapping(value = "/getBranchs")
	public List<Map<String,Object>> getBranchs(){
		List<BranchEntity> branchs = null;
		try {
			//查出所有部门信息（全部不包括在内）
			branchs = branchService.getBranchs();
		} catch (ServiceException e) {
			logger.info(""+e);
			e.printStackTrace();
		}
		branchs = BranchUtils.parseBranch(branchs);
		
		List<Map<String,Object>> objects = BranchUtils.getBranchTreeJsonData(branchs);
		return objects;
	}
	
	/**
	 * 添加部门部分
	 */
	@RequestMapping(value = "/add",method = RequestMethod.GET)
    public String add(){
		return "xServer/branch/add";
    }
	
	
	@ResponseBody
	@RequestMapping(value = "/add",method = RequestMethod.POST)
    public Map<String,Object> add(HttpServletRequest request,BranchEntity branch) {
		try {
			branchService.add(branch);
			UserSession user = (UserSession)UserManager.getSessionInfo();
			user.setRgt(user.getRgt()+2);
			UserManager.setSessionInfo(user);
			request.getSession().setAttribute(Constant.sessioninfo,user);
		} catch (ServiceException e) {
			logger.info(""+e);
			e.printStackTrace();
			return ResponseUtil.sendResult(false,e.getMessage());
		}
		return ResponseUtil.sendResult(true, "创建成功");
    }
	
	/**
	 * 更新部门部分
	* @return
	* 2014-8-18
	* @author:
	* *******************
	 */
	@RequestMapping(value = "/update",method = RequestMethod.GET)
    public String update(Integer id,Model model){
		try {
			BranchEntity branch = branchService.findById(id);
			model.addAttribute("branch",branch);
		} catch (ServiceException e) {
			logger.info(""+e);
			e.printStackTrace();
		}
		
		return "xServer/branch/update";
    }
	
	@ResponseBody
	@RequestMapping(value = "/update",method = RequestMethod.POST)
    public Map<String,Object> update(HttpServletRequest request,BranchEntity branch) {
		try {
			branchService.update(branch);
		} catch (ServiceException e) {
			logger.info(""+e);
			e.printStackTrace();
			return ResponseUtil.sendResult(false,e.getMessage());
		}
		return ResponseUtil.sendResult(true, "更新成功");
    }
	/**
	 * *******************
	* @param request
	* @param id				请求删除的部门id
	* @return
	* 2014-9-2
	* @author:
	* *******************
	 */
	@ResponseBody
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Map<String,Object> delete(HttpServletRequest request,Integer id) {
		try {
			BranchEntity branch = branchService.findById(id);
			branchService.delete(request,branch);
			
			/*修改登录用户的usersession的rgt*/
			UserSession user = (UserSession) UserManager.getSessionInfo();
			user.setRgt(user.getRgt()-2);
			UserManager.setSessionInfo(user);
			request.getSession().setAttribute(Constant.sessioninfo, user);
			
		} catch (ServiceException e) {
			logger.info(""+e);
			e.printStackTrace();
			return ResponseUtil.sendResult(false,e.getMessage());
		}
		return ResponseUtil.sendResult(true, "删除成功");
    }
	
	@RequestMapping(value = "/cardcount" , method = RequestMethod.GET)
	public String cardcount(Model model){
		SimpleDateFormat timeFormat=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		String etime = timeFormat.format(calendar.getTime());
		calendar.add(Calendar.DATE, -1);    //得到前一天
		calendar.add(Calendar.MONTH, -1);    //得到前一个月 
		String stime = timeFormat.format(calendar.getTime());
		model.addAttribute("stime",stime);
		model.addAttribute("etime",etime);
		return "arm/web/count/branchOpenCard";
	}
	
	//部门开卡统计
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/cardcount" , method = RequestMethod.POST)
	public Map<String,Object> cardcount(HttpServletRequest request,Date stime,Date etime){
		List<Map<String,Object>> cardCount = null;
		int count = 0;
		try {
			UserSession userinfo = (UserSession)UserManager.getSessionInfo();
			cardCount = branchService.cardCountList(stime, etime,userinfo.getLft(),userinfo.getRgt());
		} catch (Exception e) {
			logger.info("部门开卡统计:"+e);
			e.printStackTrace();
		}
		Map<String,Object> res = new HashMap<String, Object>();
		List<Map<String,Object>> rowData = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map : cardCount){
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("id",map.get("ID"));
	    	data.put("name",map.get("NAME"));
	    	data.put("branchid",map.get("BRANCHID"));
	    	data.put("count",map.get("C_COUNT"));
			//如果此部门存在父部门并且父部门pkid存在于branch表中，将此部门加入到data中（根部门pkid固定为1）
	    	if(map.get("FKPARENT") != null && !map.get("FKPARENT").equals(0) && parentList(cardCount,Integer.valueOf(map.get("FKPARENT").toString()))){
	    		data.put("_parentId",Integer.valueOf(map.get("FKPARENT").toString()));
	    	}
	    	rowData.add(data);
	    	if(map.get("C_COUNT")!=null){
	    		count+=Integer.parseInt(map.get("C_COUNT").toString());
	    	}else{
	    		count+=0;
	    	}
	    	
		}
		
		Map<String,Object> data2 = new HashMap<String, Object>();
		data2.put("id","0000");
    	data2.put("name","");
    	data2.put("branchid","【开卡总计】");
    	data2.put("count",count);
		
		rowData.add(data2);
		
		logger.info("===rowData===="+rowData);
	    res.put("total",rowData.size());
	    res.put("rows", rowData);
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/exportExcel",method = RequestMethod.POST)
    public void exportExcel(HttpServletRequest request,HttpServletResponse response){
		
		Map<String, Object> datas = list(null);
		List<Map<String, Object>> lists = (List<Map<String, Object>>) datas.get("rows");
		//title 表头  列名
//		String title = "\"部门编号\"\t\"部门名称\"\t\"上级部门编号\"\t\"上级部门名称\"\t\"备注\"\r\n";
		String[] title = {"部门编号","部门名称","上级部门编号","上级部门名称","备注"};
		//keys map的键名，同时按照显示的顺序排列     需和列名一致
		String[] keys = new String[]{"branchid","name","parentbranchid","parentname","remark"};
		try {
			//文件名称
			String name = "Department ";		
			logger.info("添加数据完成，开始导出");
			ExportExecl.downloadFile(ExportExecl.Expor(lists , keys, title , null , name),response);
		} catch (Exception e2) {
			logger.info(""+e2);
			e2.printStackTrace();
		}
    }
	
	@ResponseBody
	@RequestMapping(value="/exportcountExcel",method = RequestMethod.POST)
    public void exportcountExcel(HttpServletRequest request,HttpServletResponse response,Date stime,Date etime){
		
		Map<String, Object> datas = cardcount(request,stime,etime);
		List<Map<String, Object>> lists = (List<Map<String, Object>>) datas.get("rows");
		//title 表头  列名
		String[] title = {"部门编号","部门名称,","开卡量"};
		//keys map的键名，同时按照显示的顺序排列     需和列名一致
		String[] keys = new String[]{"branchid","name","count"};
		try {
			//文件名称
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String name = "BCount "+sdf.format(stime)+"-"+sdf.format(etime)+" ";		
			
			logger.info("添加数据完成，开始导出");
			ExportExecl.downloadFile(ExportExecl.Expor(lists , keys, title , null , name),response);
		} catch (Exception e2) {
			logger.info(""+e2);
			e2.printStackTrace();
		}
    }
	
	@RequestMapping(value = "/reprintcount" , method = RequestMethod.GET)
	public String reprintcount(Model model){
		SimpleDateFormat timeFormat=new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		String etime = timeFormat.format(calendar.getTime());
		calendar.add(Calendar.MONTH, -1);    //得到前一个月 
		String stime = timeFormat.format(calendar.getTime());
		model.addAttribute("stime",stime);
		model.addAttribute("etime",etime);
		return "arm/web/count/branchReceiptprint";
	}
	
	//部门回单统计
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/reprintcount" , method = RequestMethod.POST)
	public Map<String,Object> reprintcount(HttpServletRequest request,Date stime,Date etime,Integer fkbranch){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		logger.info("部门回单量统计,stime:"+sdf.format(stime)+",etime:"+sdf.format(etime)+",fkbranch"+fkbranch);
		List<Map<String,Object>> receiptCount = null;
		UserSession userinfo = (UserSession)UserManager.getSessionInfo();
		int lft=0,rgt=0;
		
		int countnum = 0,prcountnum = 0,reprcountnum = 0;
		String prconumString ="";
		String reprconumString ="";
		try {
			if(fkbranch != null){
				BranchEntity branch = branchService.findById(fkbranch);
				lft = branch.getLft();
				rgt = branch.getRgt();
			}else{
				lft = userinfo.getLft();
				rgt = userinfo.getRgt();
			}
			stime = DateUtil.getDateStart(stime);
			etime = DateUtil.getDateEnd(etime);
			receiptCount = branchService.reprintCount(stime , etime , lft , rgt);
		} catch (Exception e) {
			logger.info(e);
			e.printStackTrace();
		}
		Map<String,Object> res = new HashMap<String, Object>();
		DecimalFormat df = new DecimalFormat("#########0.00");
		List<Map<String,Object>> rowData = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map : receiptCount){
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("id",map.get("ID"));
	    	data.put("branchid",map.get("BRANCHID"));
	    	data.put("name",map.get("NAME"));
	    	data.put("count",map.get("R_COUNT"));
	    	data.put("prcount",map.get("R_PRTTIMES"));
	    	int count = (Integer) map.get("R_COUNT");
	    	int prcount = (Integer) map.get("R_PRTTIMES");
	    	data.put("prco",prcount==0||count==0?0:df.format(((double)prcount/count)*100));
	    	data.put("reprcount",map.get("R_MAKETIMES"));
	    	int reprcount = (Integer) map.get("R_MAKETIMES");
	    	data.put("reprco",reprcount==0||count==0?0:df.format(((double)reprcount/count)*100));
			//如果此部门存在父部门并且父部门pkid存在于branch表中，将此部门加入到data中（根部门pkid固定为1）
			if(map.get("FKPARENT") !=null && !map.get("FKPARENT").equals(0) && parentList(receiptCount,Integer.valueOf(map.get("FKPARENT").toString()))){
				data.put("_parentId",Integer.valueOf(map.get("FKPARENT").toString()));
			}
	    	rowData.add(data);
	    	
	    	countnum = countnum + count;
	    	
	    	prcountnum = prcountnum + prcount;
	    	
	    	reprcountnum = reprcountnum + reprcount;
	    	
		}
		
		if(countnum==0||prcountnum==0){
			prconumString = "0";
		}else{
			prconumString = df.format(((double)prcountnum/countnum)*100);  
		}
		
		if(countnum==0||reprcountnum==0){
			reprconumString = "0";
		}else{
			reprconumString = df.format(((double)reprcountnum/countnum)*100);  
		}
		
		//回单总量countnum   已打印总量：prcountnum  已打印/总量prconum  补打回单总量reprcountnum  补打回单/总量reprconum  
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("id","000");
		data.put("branchid","【统计功能】");
    	data.put("name","统计：");
    	data.put("count",countnum);
    	data.put("prcount",prcountnum);
    	data.put("prco",prconumString);
    	data.put("reprcount",reprcountnum);
    	data.put("reprco",reprconumString);
    	rowData.add(data);
		
		logger.info("======"+rowData);
	    res.put("total",rowData.size());
	    res.put("rows", rowData);
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value="/exportreprintExcel",method = RequestMethod.POST)
    public void exportreprintExcel(HttpServletRequest request,HttpServletResponse response,Date stime,Date etime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		logger.info("导出部门回单量信息,stime:"+sdf.format(stime)+",etime:"+sdf.format(etime));
		Map<String, Object> datas = reprintcount(request,stime,etime, null);
		List<Map<String, Object>> lists = (List<Map<String, Object>>) datas.get("rows");
		//title 表头  列名
//		String title = "\"部门编号\"\t\"部门名称\"\t\"回单总量\"\t\"已打印总量\"\t\"已打印/总量（%）\"\t\"补打回单总量\"\t\"补打回单/总量（%）\"\r\n";
		String[] title = {"部门编号","部门名称","回单总量","已打印总量","已打印/总量（%）","补打回单总量","补打回单/总量（%）"};
		//keys map的键名，同时按照显示的顺序排列     需和列名一致
		String[] keys = new String[]{"branchid","name","count","prcount","prco","reprcount","reprco"};
		try {
			//文件名称
			String name = "BreceiptCount "+sdf.format(stime)+"-"+sdf.format(etime)+" ";		
			
			ExportExecl.downloadFile(ExportExecl.Expor(lists , keys, title , null , name),response);
		} catch (Exception e2) {
			logger.info(e2);
			e2.printStackTrace();
		}
    }
	@RequestMapping(value = "/devicecount" , method = RequestMethod.GET)
	public String devicecount(Model model){
		return "arm/web/count/branchDeviceNum";
	}
	//部门回单机统计
	@ResponseBody
	@RequestMapping(value = "/devicecount" , method = RequestMethod.POST)
	public Map<String,Object> devicecount(HttpServletRequest request,Integer fkbranch){
		logger.info("部门设备量信息,fkbranch:"+fkbranch);
		List<Map<String,Object>> deviceCount = null;
		int count = 0;
		try {
			BranchEntity branch = null;
			if(fkbranch != null){
				branch = branchService.findById(fkbranch);
			}else{
				UserSession userinfo = (UserSession)UserManager.getSessionInfo();
				branch = branchService.findById(userinfo.getBid());
			}
			deviceCount = branchService.deviceCount(branch.getLft(),branch.getRgt());
		} catch (ServiceException e) {
			logger.info(e);
			e.printStackTrace();
		}
		Map<String,Object> rst = new HashMap<String, Object>();
		List<Map<String,Object>> rowsData = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map : deviceCount){
			Map<String,Object> data = new HashMap<String, Object>();
			data.put("id",map.get("ID"));
			data.put("branchid",map.get("BRANCHID"));
			data.put("name",map.get("NAME"));
			data.put("count",map.get("D_COUNT"));
			//如果此部门存在父部门并且父部门pkid存在于branch表中，将此部门加入到data中（根部门pkid固定为1）
			if(map.get("FKPARENT") !=null && !map.get("FKPARENT").equals(0) && parentList(deviceCount,Integer.valueOf(map.get("FKPARENT").toString()))){
				data.put("_parentId",Integer.valueOf(map.get("FKPARENT").toString()));
			}
			rowsData.add(data);
			if(map.get("D_COUNT")!=null){
		    	count+=Integer.parseInt(map.get("D_COUNT").toString());
	    	}else{
	    		count+=0;
	    	}
		}
		
		Map<String,Object> data2 = new HashMap<String, Object>();
		data2.put("name","");
    	data2.put("id","0000");
    	data2.put("branchid","【部门设备量统计总计】");
    	data2.put("count",count);
    	rowsData.add(data2);
		
		logger.info("======="+rowsData);
	    rst.put("total",rowsData.size());
	    rst.put("rows", rowsData);
		return rst;
	}
	//导出部门设备量统计
	@ResponseBody
	@RequestMapping(value="/exportdnumExcel",method = RequestMethod.POST)
    public void exportdnumExcel(HttpServletRequest request,HttpServletResponse response,Integer fkbranch){
		logger.info("导出部门设备量信息:fkbranch:"+fkbranch);
		Map<String, Object> datas = devicecount(request, null);
		List<Map<String, Object>> lists = (List<Map<String, Object>>) datas.get("rows");
		//title 表头  列名
		String[] title = {"部门编号","部门名称","设备总量"};
		//keys map的键名，同时按照显示的顺序排列     需和列名一致
		String[] keys = new String[]{"branchid","name","count"};
		try {
			//文件名称
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String name = "BDeviceCount ";		
			
			ExportExecl.downloadFile(ExportExecl.Expor(lists , keys, title , null , name),response);
		} catch (Exception e2) {
			logger.info("导出部门设备量统计:"+e2);
			e2.printStackTrace();
		}
    }
	
	
	/**判断父部门pkid是否存在于branch表中 */
	private boolean parentList(List<Map<String,Object>> deviceCount,int parent){
		for(Map<String,Object> map : deviceCount){
			if(parent == Integer.valueOf(map.get("ID").toString()))
				return true;
		}
		return false;
	}
}
