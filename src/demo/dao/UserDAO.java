package demo.dao;

import java.util.List;
import java.util.Map;

import demo.Entity.UserEntity;
import demo.exception.DAOException;

public interface UserDAO {
	
	public UserEntity findByUserid(String userid) throws DAOException;
	
	public List<UserEntity>	findAll(Map<String,Object> map) throws DAOException;
	//按用户的角色id查询
	public List<UserEntity> findFkroleid(Integer roleid) throws DAOException;
	//按用户的部门id查询
	public List<UserEntity> findFkbranchid(Integer branchid) throws DAOException;

	public UserEntity findId(Integer id) throws DAOException;
	
	public void update(UserEntity user) throws DAOException;
	
	public void save(UserEntity user) throws DAOException;	
	
}
