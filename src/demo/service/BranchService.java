package demo.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import demo.Entity.BranchEntity;
import demo.exception.ServiceException;


public interface BranchService extends BaseService {
	public List<BranchEntity>	list(String name,String branchid) throws ServiceException;

	public BranchEntity findById(Integer id) throws ServiceException;
	
	public void	add(BranchEntity branch) throws ServiceException;
	
	public void	update(BranchEntity branch) throws ServiceException;
	
	public void	update2(BranchEntity branch) throws ServiceException;
	
	public void delete(HttpServletRequest request, BranchEntity branch) throws ServiceException;
	
	public List<BranchEntity> getBranchs() throws ServiceException;
	
	public List cardCountList(Date stime,Date etime,Integer lft,Integer rgt) throws ServiceException;
	
	public List reprintCount(Date stime,Date etime,Integer lft,Integer rgt) throws ServiceException;
	
	public List deviceCount(Integer lft,Integer rgt) throws ServiceException;
}
