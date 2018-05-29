package demo.service.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import demo.Entity.RoleFunctionMap;
import demo.Entity.SessionInfo;
import demo.Entity.UserEntity;
import demo.Entity.UserSession;
import demo.common.BeanUtils;
import demo.common.EncryptUtil;
import demo.common.IPUtils;
import demo.common.PageData;
import demo.common.UserManager;
import demo.dao.RoleFunctionmapDAO;
import demo.dao.UserDAO;
import demo.exception.DAOException;
import demo.exception.ServiceException;
import demo.service.UserService;


@Service("iuserService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {
	private static final Log logger = LogFactory.getLog(UserServiceImpl.class);
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private RoleFunctionmapDAO rolefunctionmapDAO;
	
	@Override
	public SessionInfo login(HttpServletRequest request,String userid, String password) throws ServiceException{
		//获取用户
		UserEntity user = null;
			try {
				user = userDAO.findByUserid(userid);
			} catch (DAOException e1) {
				e1.printStackTrace();
			}
		if(user==null){
			throw new ServiceException("login.user.null");
		}
		//判断密码 通过salt进行判断
		if(!EncryptUtil.authenticate(password, user.getPassword(), user.getSalt())){
			throw new ServiceException("login.password.error");
		}
		if(user.getStatus() == 2){
			throw new ServiceException("该账号已被锁定，暂时无法登陆");
		}
		if(user.getStatus() == 9){
			throw new ServiceException("该账号已被禁用，无法登陆");
		}
		if(user.getStatus() != 1){
			throw new ServiceException("该账号状态异常，无法登陆");
		}
		
		
		SessionInfo sessionInfo = null;
		try {
			List<RoleFunctionMap> rolefunctions = rolefunctionmapDAO.findRoleId(user.getRole().getId());
			user.getRole().setRoleFunctionMaps(rolefunctions);
			sessionInfo = new UserSession(user);
			UserManager.setSessionInfo(sessionInfo);
			
			user.setLltime(new Date());
			user.setLlipaddr(IPUtils.getIpAddr(request));
			userDAO.update(user);
		} catch (Exception e) {
			logger.info(""+e);
			throw new ServiceException("更新用户登录信息失败");
		}
		return sessionInfo;
	}
	
	@Override
	public UserEntity updatePwd(HttpServletRequest request,String userid, String password,String newpassword) throws ServiceException{
		//获取用户
		logger.info("---进入修改密码方法：updatePwd（）----");
		UserEntity user = null;
		try{
			user = userDAO.findByUserid(userid);
		}catch(Exception e){
			logger.info("修改密码："+e);
			throw new ServiceException(e);
		}
		if(user==null){
			throw new ServiceException("输入的用户名为空");
		}
		//判断密码 通过salt进行判断
		if(!EncryptUtil.authenticate(password, user.getPassword(), user.getSalt())){
			throw new ServiceException("输入的原密码错误");
		}
		if(user.getStatus() == 2){
			throw new ServiceException("该账号已被锁定，无法修改密码");
		}
		if(user.getStatus() == 9){
			throw new ServiceException("该账号已被禁用，无法修改密码");
		}
		if(user.getStatus() != 1){
			throw new ServiceException("该账号状态异常，无法修改密码");
		}
		
		try {
			user.setLltime(new Date());
			user.setLlipaddr(IPUtils.getIpAddr(request));
			user.setPassword(newpassword);
			
			if(user.getPassword()!=null && user.getPassword().length()>0){
				String salt = new String(EncryptUtil.getSalt());
				user.setSalt(salt);
				user.setPassword(EncryptUtil.getEncryptedPassword(user.getPassword(),salt));
			}
			userDAO.update(user);
		} catch (Exception e) {
			logger.info(""+e);
			throw new ServiceException("更新用户登录信息失败");
		}
		
		return user;
	}
	
	
	@Override
	public void logout() throws ServiceException {
		
	}

	@Override
	public UserEntity findByUserid(String userid) throws ServiceException {
		UserEntity user=null;
		try {
			user=userDAO.findByUserid(userid);
		} catch (Exception e) {
			logger.info(""+e);
		}
		return user;
	}


	@Override
	public PageData<UserEntity> list(Integer rows, Integer page, String sort,String order, int lft, int rgt, Integer fkbranch, String userid)throws ServiceException {
		logger.info("==查询用户：rows:"+rows+",page:"+page+",sort:"+sort+",order:"+order+",lft:"+lft+",rgt:"+rgt+",fkbranch:"+fkbranch+",userid:"+userid);
		PageHelper.startPage(page,rows);
		PageData<UserEntity> pagedata = null;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sort",sort);
		map.put("order",order);
		map.put("lft",lft);
		map.put("rgt",rgt);
		map.put("fkbranch",fkbranch);
		map.put("userid",userid);
		try{
			List<UserEntity> users = userDAO.findAll(map);
			PageInfo<UserEntity> p = new PageInfo<UserEntity>(users);
			pagedata = new PageData<UserEntity>((int) p.getTotal(),p.getSize(),p.getPageNum(),users);
		}catch (Exception e) {
			logger.info(""+e);
			throw new ServiceException("查询用户出错",e);
		}
		return pagedata;
	}


	@Override
	public void save(UserEntity user) throws ServiceException {
		logger.info("开始添加用户");
		try{
			if(user.getName()==null||user.getName().length()==0||user.getUserid()==null||user.getUserid().length()==0){
				logger.info("用户信息出错，用户名或用户id不能为空");
				throw new Exception("用户信息出错,用户名或用户id不能为空");
			}
			String userid = user.getUserid();
			UserEntity usere = userDAO.findByUserid(userid);
			if(usere!=null){
				logger.info("用户信息出错，用户id已存在或用户名或用户id为空");
				throw new Exception("用户信息出错,用户id已存在或用户名或用户id为空");
			}
			user.setStatus(1);
			String salt = new String(EncryptUtil.getSalt());
			user.setSalt(salt);
			user.setPassword(EncryptUtil.getEncryptedPassword("888888",user.getSalt()));
			user.setFkrole(user.getRole().getId());
			user.setFkbranch(user.getBranch().getId());
			userDAO.save(user);
			logger.info("添加用户成功");
		}catch (Exception e) {
			logger.info("用户添加异常："+e);
			e.printStackTrace();
			throw new ServiceException("添加用户出错",e);
		}
	}
	@Override
	public void update(UserEntity user) throws ServiceException {
		logger.info("开始修改用户信息"+user);
		if(user.getUserid().equals("resetpassword")){
			try {
				user = userDAO.findId(user.getId());
				user.setPassword("888888");
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("用户信息出错"+e);
				throw new ServiceException("用户信息出错,重置失败");
			}
		}
		if(user.getPassword()!=null && user.getPassword().length()>0){
			String salt = new String(EncryptUtil.getSalt());
			user.setSalt(salt);
			user.setPassword(EncryptUtil.getEncryptedPassword(user.getPassword(),salt));
		}
		try {
			if(user.getName()==null||user.getName().length()==0||user.getUserid()==null||user.getUserid().length()==0){
				logger.info("用户信息出错，用户名或用户id不能为空");
				throw new Exception("用户信息出错,用户名或用户id不能为空");
			}
			UserEntity entity = userDAO.findId(user.getId());
			if(!user.getUserid().equals(entity.getUserid())&&userDAO.findByUserid(user.getUserid())!=null){
				throw new Exception("用户信息出错，用户id已存在");
			}
			try {
				entity = BeanUtils.copyNotNullProperties(user,entity);
			} catch (Exception e) {
				logger.info(""+e);
				e.printStackTrace();
			}
			entity.setFkrole(user.getRole().getId());
			entity.setFkbranch(user.getBranch().getId());
			userDAO.update(entity);
			logger.info("修改用户信息成功");
		} catch (Exception e) {
			logger.info(""+e);
			e.printStackTrace();
		}
		
	}
	@Override
	public UserEntity findById(Integer id) throws ServiceException {
		UserEntity user = null;
		try {
			user = userDAO.findId(id);
		} catch (Exception e) {
			logger.info(""+e);
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void delete(UserEntity user) throws ServiceException{
		logger.info("开始调用删除用户的方法   要删除的用户ID为"+user.getId());
		try {
			if(userDAO.findId(user.getId())==null ){
				throw new Exception("删除用户失败");
			}
			user.setStatus(99);
			userDAO.update(user);
			logger.info("删除用户成功");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("删除用户失败"+e);
			throw new ServiceException("系统内部错误");
		}
	}

	@Override
	public List<UserEntity> Useridlist() throws ServiceException {
		Map<String,Object> map = new HashMap<String, Object>();
		List<UserEntity> users = null;
		try {
			users = userDAO.findAll(map);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return users;
	}
}
