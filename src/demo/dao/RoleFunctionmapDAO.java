package demo.dao;

import java.util.List;
import java.util.Map;

import demo.Entity.RoleFunctionMap;
import demo.exception.DAOException;

public interface RoleFunctionmapDAO {
	
	public void deleteRoleAll(Integer fkroleid) throws DAOException;
	
	public List<RoleFunctionMap> findRoleId(Integer id) throws DAOException;
	
	public void save(RoleFunctionMap roleFunctionMap) throws DAOException;
	
	public void updateRole(Map<String,Object> map) throws DAOException;
}
