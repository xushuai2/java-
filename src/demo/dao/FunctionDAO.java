package demo.dao;

import java.util.List;

import demo.Entity.FunctionEntity;
import demo.exception.DAOException;


public interface FunctionDAO{
	
	public List<FunctionEntity> findAll() throws DAOException;

	public FunctionEntity get(Integer id) throws DAOException;
	
	public void update(FunctionEntity function) throws DAOException;
	
	public void save(FunctionEntity function) throws DAOException;
}
