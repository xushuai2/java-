package demo.dao;

import java.util.List;
import java.util.Map;

import demo.Entity.RoleEntity;
import demo.exception.DAOException;


public interface RoleDAO {

	public List<RoleEntity>	findAll(Map<String,Object> map) throws DAOException;

	public RoleEntity findRoleName(String rolename) throws DAOException;

	public RoleEntity findId(Integer id) throws DAOException;

	public void update(RoleEntity role) throws DAOException;
	
	public void save(RoleEntity role) throws DAOException;
}
