package demo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import demo.Entity.FunctionEntity;
import demo.dao.FunctionDAO;
import demo.service.FunctionService;
@Service("functionService")
public class FunctionServiceImpl extends BaseServiceImpl implements FunctionService {
	private static final Log logger = LogFactory.getLog(FunctionServiceImpl.class);
	
	@Resource
	private FunctionDAO functionDAO;
	
	@Override
	public List<FunctionEntity> getAll() throws Exception{
		List<FunctionEntity> functions = null;
		try {
			functions = functionDAO.findAll();
		} catch (Exception e) {
			logger.error("执行获取function失败",e);
			throw new Exception("执行获取function失败",e);
		}
		return functions;
	}
	
}
