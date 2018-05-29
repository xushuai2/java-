package demo.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import demo.Entity.SessionInfo;
import demo.Entity.UserEntity;
import demo.common.PageData;
import demo.exception.ServiceException;

public interface UserService extends BaseService {
	/**
	 * 用户登录
	* @param userid
	* @param password
	 */
	public SessionInfo login(HttpServletRequest request,String userid, String password) throws ServiceException;
	
	public void logout() throws ServiceException;
	
	public UserEntity findById(Integer id) throws ServiceException;
	
	public UserEntity findByUserid(String userid) throws ServiceException;
	
	public List<UserEntity> Useridlist() throws ServiceException;
	
	public PageData<UserEntity> list(Integer rows, Integer page, String sort,
			String order, int lft, int rgt, Integer fkbranch, String userid)
			throws ServiceException;
	
	public void save(UserEntity user) throws ServiceException;
	
	public void update(UserEntity user) throws ServiceException;
	
	public void delete(UserEntity user) throws ServiceException;

	//xushuai-20150930
	public UserEntity updatePwd(HttpServletRequest request, String userid, String password, String newpassword)  throws ServiceException;
}
