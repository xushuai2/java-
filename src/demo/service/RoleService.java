package demo.service;

import java.util.List;

import demo.Entity.RoleEntity;
import demo.exception.ServiceException;

public interface RoleService extends BaseService {
	
	public RoleEntity findById(Integer id) throws ServiceException;
	
	public List<RoleEntity>	list(String sort,String order,String name) throws ServiceException;
	
	public void	save(RoleEntity role) throws ServiceException;
	
	public void	update(RoleEntity role) throws ServiceException;
	
	public void delete(RoleEntity role) throws ServiceException;
	
}
