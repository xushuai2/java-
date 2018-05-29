package demo.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.Entity.BranchEntity;
import demo.Entity.UserSession;
import demo.common.BeanUtils;
import demo.common.DateUtil;
import demo.common.UserManager;
import demo.dao.BranchDAO;
import demo.dao.UserDAO;
import demo.exception.DAOException;
import demo.exception.ServiceException;
import demo.service.BranchService;


@Service("branchService")
public class BranchServiceImpl extends BaseServiceImpl implements BranchService {
	private static final Log logger = LogFactory.getLog(BranchServiceImpl.class);
	@Autowired
	private BranchDAO branchDAO;
	@Autowired
	private UserDAO userDAO;
	
	private static int num = 0;
	
	
	@Override
	public List<BranchEntity> list(String name,String branchid) throws ServiceException{
		List<BranchEntity> branchs = new ArrayList<BranchEntity>();
		logger.info("开始查询部门");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name",name);
		map.put("branchid",branchid);
		UserSession user = (UserSession)UserManager.getSessionInfo();
		logger.info("==========lft="+user.getLft()+"======rgt="+user.getRgt());
		map.put("lft",user.getLft());
		map.put("rgt",user.getRgt());
		try {
			branchs = branchDAO.findAll(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return branchs;
	}
	
	
	@Override
	public List<BranchEntity> getBranchs() throws ServiceException{
		List<BranchEntity> branchs = new ArrayList<BranchEntity>();
		logger.info("开始查询部门");
		Map<String,Object> map = new HashMap<String, Object>();
		UserSession user = (UserSession)UserManager.getSessionInfo();
		map.put("lft",user.getLft());
		map.put("rgt",user.getRgt());
		try {
			branchs = branchDAO.findAll(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return branchs;
	}
	@Override
	public BranchEntity findById(Integer id) throws ServiceException {
		BranchEntity branch = null;
		try {
			branch = branchDAO.get(id);
		} catch (DAOException e) {
			logger.info(""+e);
			e.printStackTrace();
			throw new ServiceException("系统内部错误");
		}
		return branch;
	}
	@Override
	public void add(BranchEntity branch) throws ServiceException {
		//首先看部门id是否存在
		logger.info("增加部门 部门编号:"+branch.getBranchid());
		try {
			if(branchDAO.findbranchid(branch.getBranchid())!=null){
				throw new ServiceException("该部门编号已存在");
			}
			if(branch.getName().equals("")){
				throw new ServiceException("该部门名称不能为空");
			}
			if(branch.getParent().getId().equals("")){
				throw new ServiceException("上级不能为空");
			}
			branch.setFkparent(branch.getParent().getId());
			branch.setStatus(1);
			branch.setLft(0);
			branch.setRgt(0);
			branchDAO.save(branch);
			List<BranchEntity> branchs = branchDAO.getAllBranch();
			branchs = branchlftrgt(branchs,0);
			for(BranchEntity a:branchs){
				branchDAO.update(a);
			}
			num = 0;
			logger.info("增加部门成功 部门编号："+branch.getBranchid());
		} catch (DAOException e) {
			logger.info(""+e);
			e.printStackTrace();
			logger.error("增加部门失败:"+e);
			throw new ServiceException("系统内部错误");
		}
		
	}
	
	
	/*部门左右支排序*/
	public static List<BranchEntity> branchlftrgt(List<BranchEntity> branchs,int parent){
		for (BranchEntity branch : branchs) {
			if(branch.getFkparent() == parent){
				branch.setLft(num);
				num++;
				branchlftrgt(branchs, branch.getId());
				branch.setRgt(num);
				num++;
			}
		}
		return branchs;
	}

	
	@Override
	public void update(BranchEntity branch) throws ServiceException {
		logger.info("更新部门  部门编号："+branch.getBranchid());
		try {
			BranchEntity entity = branchDAO.get(branch.getId());
			if(branchDAO.findbranchid(branch.getBranchid())!=null && !entity.getBranchid().equals(branch.getBranchid())){
				throw new ServiceException("该部门编号已存在");
			}
			if(branch.getName().equals("")){
				throw new ServiceException("部门名称不能为空");
			}
			if(branch.getParent().getId()==entity.getId()||branch.getParent().getId().equals("")){
				throw new ServiceException("上级部门不能为空，且不能为自己");
			}
			try {
				entity = BeanUtils.copyNotNullProperties(branch,entity);
				entity.setFkparent(branch.getParent().getId());
			} catch (Exception e) {
				logger.info(""+e);
				e.printStackTrace();
			}
			branchDAO.update(entity);
			//更新部门的左右支点start
			List<BranchEntity> branchs = branchDAO.getAllBranch();
			branchs = branchlftrgt(branchs,0);
			for(BranchEntity a:branchs){
				branchDAO.update(a);
			}
			num = 0;
			logger.info("更新部门成功  部门编号："+branch.getBranchid());
		} catch (DAOException e) {
			logger.info(""+e);
			e.printStackTrace();
			logger.info("更新部门失败   部门编号："+branch.getBranchid());
			throw new ServiceException("系统内部错误");
		}
	}
	
	@Override
	public void delete(HttpServletRequest request, BranchEntity branch) throws ServiceException {
		logger.info("删除部门  部门编号："+branch.getBranchid());
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("lft",branch.getLft());
			map.put("rgt",branch.getRgt());
			if(userDAO.findFkbranchid(branch.getId()).size() != 0 || branchDAO.findAll(map).size() >1){
				throw new ServiceException("该部门有员工或者有下级部门，不能删除");
			}
			branch.setStatus(99);
			branchDAO.update(branch);
			//更新部门的左右支点start
			List<BranchEntity> branchs = branchDAO.getAllBranch();
			branchs = branchlftrgt(branchs,0);
			for(BranchEntity a:branchs){
				branchDAO.update(a);
			}
			num = 0;
			logger.info("删除部门成功   部门编号："+branch.getBranchid());
		} catch (DAOException e) {
			logger.info(""+e);
			e.printStackTrace();
			logger.info("删除部门失败  部门编号："+branch .getBranchid());
			throw new ServiceException("系统内部错误");
		}
	}
	@Override
	public List<Map<String,Object>> cardCountList(Date stime, Date etime,Integer lft,Integer rgt) throws ServiceException {
		List<Map<String,Object>> countList = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("==部门开卡统计：stime:"+sdf.format(stime)+",etime:"+sdf.format(etime)+",lft:"+lft+",rgt+"+rgt);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("lft",lft);
		map.put("rgt",rgt);
		try {
			String st = null,et = null;
			if(stime != null)st=sdf.format(DateUtil.getDateStart(stime));
			if(etime != null)et=sdf.format(DateUtil.getDateEnd(etime));
			map.put("stime",st);
			map.put("etime",et);
			countList = branchDAO.cardCountList(map);
		} catch (Exception e) {
			logger.info(""+e);
			throw new ServiceException("查询部门开卡统计失败");
		}
		return countList;
	}
	@Override
	public List<Map<String,Object>> reprintCount(Date stime, Date etime,Integer lft,Integer rgt) throws ServiceException {
		List<Map<String,Object>> reprintcount = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("===部门回单量统计：stime:"+sdf.format(stime)+",etime:"+sdf.format(etime)+",lft:"+lft+",rgt:"+rgt);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("lft",lft);
		map.put("rgt",rgt);
		try {
			String st = null,et = null;
			if(stime != null)st=sdf.format(DateUtil.getDateStart(stime));
			if(etime != null)et=sdf.format(DateUtil.getDateEnd(etime));
			map.put("stime",st);
			map.put("etime",et);
			reprintcount = branchDAO.branchReceiptCount(map);
		} catch (Exception e) {
			logger.info("查询部门回单统计失败"+e);
			throw new ServiceException("查询部门回单统计失败");
		}
		return reprintcount;
	}
	@Override
	public List<Map<String,Object>> deviceCount(Integer lft,Integer rgt) throws ServiceException {
		logger.info("=====部门设备统计：lft:"+lft+",rgt:"+rgt);
		List<Map<String,Object>> devicecount = null;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("lft",lft);
		map.put("rgt",rgt);
		try {
			devicecount = branchDAO.branchDeviceCount(map);
			logger.info("=============================devicecount.size:"+devicecount.size());
		} catch (Exception e) {
			logger.info("查询部门设备统计失败"+e);
			throw new ServiceException("查询部门设备统计失败");
		}
		return devicecount;
	}


	@Override
	public void update2(BranchEntity branch) throws ServiceException {
		try {
			branchDAO.update(branch);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
	}
}
