package demo.service;

import java.util.List;

import demo.Entity.FunctionEntity;
public interface FunctionService extends BaseService {
	List<FunctionEntity>  getAll() throws Exception;
}
