package demo.dao;

import java.util.List;
import java.util.Map;

import demo.Entity.BranchEntity;
import demo.exception.DAOException;


public interface BranchDAO{
	public List<BranchEntity>	findAll(Map<String,Object> map) throws DAOException;
	
	public List<BranchEntity>	getAllBranch() throws DAOException;
	
	public BranchEntity findbranchid(String branchid) throws DAOException;
	//部门开卡统计
	public List<Map<String,Object>> cardCountList(Map<String,Object> map) throws DAOException;
	//部门回单量统计
	public List<Map<String,Object>> branchReceiptCount(Map<String,Object> map) throws DAOException;
	//部门设备统计
	public List<Map<String,Object>> branchDeviceCount(Map<String,Object> map) throws DAOException;
	
	public BranchEntity get(Integer id) throws DAOException;
	
	public void update(BranchEntity branch) throws DAOException;
	
	public void save(BranchEntity branch) throws DAOException;
}
