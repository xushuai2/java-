package demo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.Entity.RoleEntity;
import demo.Entity.RoleFunctionMap;
import demo.Entity.UserSession;
import demo.common.BeanUtils;
import demo.common.UserManager;
import demo.dao.RoleDAO;
import demo.dao.RoleFunctionmapDAO;
import demo.dao.UserDAO;
import demo.exception.DAOException;
import demo.exception.ServiceException;
import demo.service.RoleService;
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {
	private static final Log logger = LogFactory.getLog(RoleServiceImpl.class);
	
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private RoleFunctionmapDAO rfDAO;
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public List<RoleEntity> list(String sort,String order,String name) throws ServiceException {
		UserSession user = (UserSession) UserManager.getSessionInfo();
		List<RoleEntity> roles = null;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sort",sort);
		map.put("order",order);
		map.put("name", name);
		map.put("level",user.getLevel()+1);
		try{
			roles = roleDAO.findAll(map);
		}catch (DAOException e) {
			logger.info(""+e);
			throw new ServiceException("查询角色出错",e);
		}
		return roles;
	}
	@Override
	public RoleEntity findById(Integer id) throws ServiceException {
		logger.info("开始查询角色信息 查询的角色id为："+id);
		RoleEntity role = null;
		try{
			role = roleDAO.findId(id);
			role.setRoleFunctionMaps(rfDAO.findRoleId(role.getId()));
			logger.info("查询角色成功");
		}catch (DAOException e) {
			logger.info("查询角色失败"+e);
			throw new ServiceException("查询角色出错",e);
		}
		logger.info("");
		return role;
	}
	@Override
	public void	save(RoleEntity role) throws ServiceException{
		logger.info("开始调用添加角色  角色名称："+role.getName());
		try {
			if(roleDAO.findRoleName(role.getName())!=null||role.getName().equals("")){
				throw new ServiceException("添加角色失败,该角色名已存在");
			}
			List<RoleFunctionMap> rfs = role.getRoleFunctionMaps();
			roleDAO.save(role);
			logger.info("增加角色成功 角色名称："+role.getName());
			role = roleDAO.findRoleName(role.getName());
			for (RoleFunctionMap rf : rfs) {
				rf.setFkrole(role.getId());
				rfDAO.save(rf);
			}
		} catch (DAOException e) {
			logger.error("增加角色失败:"+e);
			e.printStackTrace();
			throw new ServiceException("系统内部错误");
		}
	}
	@Override
	public void	update(RoleEntity role) throws ServiceException{
		logger.info("开始调用修改角色  角色名称："+role.getName()+"角色ID:"+role.getId());
		try {
			RoleEntity roleEntity= findById(role.getId());
			if(!roleEntity.getName().equals(role.getName())&&roleDAO.findRoleName(role.getName())!=null||role.getName().equals("")){//角色名称修改后要判断是否已存在
				logger.info("修改角色失败,该角色名已存在");
				throw new ServiceException("修改角色失败,该角色名已存在");
			}
			try {
				roleEntity=BeanUtils.copyNotNullProperties(role,roleEntity);
			} catch (Exception e) {
				logger.info(""+e);
				e.printStackTrace();
			}
			roleDAO.update(roleEntity);
			List<RoleFunctionMap> rfList1= role.getRoleFunctionMaps();
			List<RoleFunctionMap> rfList2 = roleEntity.getRoleFunctionMaps();
			for(RoleFunctionMap rf : rfList1){
				boolean yes = true;
				RoleFunctionMap rolefunctionmap = new RoleFunctionMap();
				for(RoleFunctionMap rf2 : rfList2){
					if(rf.getFkfunction().equals(rf2.getFkfunction())){
						rolefunctionmap = rf2;
						yes = false;
						break;
					}
				}
				if(yes){
					rf.setFkrole(role.getId());
					rfDAO.save(rf);
				}else{
					rfList2.remove(rolefunctionmap);
				}
			}
			String functionids = "";
			if(rfList2.size()>0){
				for (RoleFunctionMap rf : rfList2) {
					functionids += "'"+rf.getFkfunction()+"',";
				}
				logger.info("==functions:"+functionids+"==="+functionids.substring(0,functionids.length()-1)+"===");
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("fkroleid",role.getId());
				map.put("fkfunctions",functionids.substring(0,functionids.length()-1));
				rfDAO.updateRole(map);
			}
			logger.info("修改角色成功 角色名称："+role.getName());
		} catch (DAOException e) {
			logger.info(""+e);
			e.printStackTrace();
			logger.error("修改角色失败:"+e);
			throw new ServiceException("系统内部错误");
		}
	}
	
	@Override
	public void delete(RoleEntity role) throws ServiceException{
		logger.info("开始调用删除角色的方法   要删除的角色ID为"+role.getId());
		try {
			logger.info("======================"+roleDAO.findId(role.getId()));
			logger.info("======================"+userDAO.findFkroleid(role.getId()).size());
			if(roleDAO.findId(role.getId())==null || userDAO.findFkroleid(role.getId()).size() != 0){
				throw new ServiceException("删除角色失败,该角色有用户，不能删除");
			}
			role.setStatus(99);
			roleDAO.update(role);
			rfDAO.deleteRoleAll(role.getId());
			logger.info("删除角色成功");
		} catch (DAOException e) {
			logger.info(""+e);
			logger.info("删除角色失败");
			throw new ServiceException("系统内部错误");
		}
	}
}
