package demo.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import demo.Entity.FunctionEntity;
import demo.Entity.RoleEntity;
import demo.Entity.RoleFunctionMap;

public class FunctionUtils {
	private static final Log logger = LogFactory.getLog(FunctionUtils.class);
	public static List<FunctionEntity> getRoleMenus(List<FunctionEntity> functions,Map<String,Integer> permission){
		List<FunctionEntity> menus = new ArrayList<FunctionEntity>();
		if(functions!=null){
			Iterator<FunctionEntity> itor = functions.iterator();
			while(itor.hasNext()){
				FunctionEntity function = itor.next();
				FunctionEntity f = new FunctionEntity();
				if(function.getChildren().size()>0){
					f.getChildren().addAll(getRoleMenus(function.getChildren(),permission));
				}
				Integer per = permission.get(function.getId());
				//把子项全部加进去
				if((function.getMenu()==1 && per!=null && per.intValue()>0) || f.getChildren().size()>0){
					f.setId(function.getId());
					f.setLevel(function.getLevel());
					f.setMenu(function.getMenu());
					f.setName(function.getName());
					f.setUrl(function.getUrl());
					menus.add(f);
				}
			}
		}
		return menus;
	}
	
	
	
	/**
	 * 把所有的function放进一个map内 在登录的时候进行权限转换使用
	 */
	public static HashMap<String,String> getUrlKeyMap(List<FunctionEntity> functions){
		HashMap<String,String> mapUrlKey = new HashMap<String,String>();
		if(functions!=null){
			Iterator<FunctionEntity> itor = functions.iterator();
			while(itor.hasNext()){
				FunctionEntity function = itor.next();
				if(function.getUrl()!=null && function.getUrl().length()>0){
					mapUrlKey.put(function.getUrl(),function.getId());
					logger.info("url:"+function.getUrl()+" key:"+function.getId());
				}
				if(function.getChildren()!=null && function.getChildren().size()>0){
					mapUrlKey.putAll(getUrlKeyMap(function.getChildren()));
				}
				//这里是白名单的处理,一个权限可以对应几个url,以“;”分割
				if(function.getWhitelist()!=null && function.getWhitelist().length()>0){
					String urlArray[] = function.getWhitelist().split(";");
					for(int i=0;i<urlArray.length;i++){
						mapUrlKey.put(urlArray[i],function.getId());
						logger.info("url:"+urlArray[i]+" key:"+function.getId());
					}
				}
			}
		}
		return mapUrlKey;
	}
	
	public static List<Map<String,Object>> getRoleFunctions(List<FunctionEntity> functions,RoleEntity role){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		//这是角色的所有已有权限
		List<RoleFunctionMap> roleFunctions = role.getRoleFunctionMaps();					
		//将所有权限放到hashmap去后边好查询
		HashMap<String,Integer> rolePermission = new HashMap<String,Integer>();
		for(RoleFunctionMap rfm:roleFunctions){
			rolePermission.put(rfm.getFkfunction(),rfm.getPurview()); 
		}
		int level = role.getLevel();	//角色的级别
		for(FunctionEntity f : functions){
			if(level<=f.getLevel()){			//只有级别小的才显示 0超级管理员 1系统管理员 2部门管理员3用户
				Map<String,Object> data = new HashMap<String, Object>();
				Integer per = rolePermission.get(f.getId());
				data.put("id",f.getId());
				data.put("name",f.getName());
				data.put("url",f.getUrl());
				boolean bPur = false;
				if(f.getUrl()!=null && f.getUrl().length()>0){
					bPur = true;
				}
				data.put("purno",bPur);
				data.put("puryes",bPur);
				data.put("pur",per==null?0:per);
				if(f.getParent()!=null){
					data.put("_parentId",f.getParent().getId());
				}
				result.add(data);
				//递归
				if(f.getChildren()!=null){
					result.addAll(getRoleFunctions(f.getChildren(),role));
				}
			}
		}
		
		return result;
	}
	
	
	/*
	 * 将所有的function分层，即子类放在父类的list里面
	 * */
	public static List<FunctionEntity> functionLevel(List<FunctionEntity> functions,String fkparent){
		List<FunctionEntity> fs = new ArrayList<FunctionEntity>();
		for(FunctionEntity function : functions){
			if (function.getFkparent().equals(fkparent)) {
				function.setChildren(functionLevel(functions,function.getId()));
				fs.add(function);
			}
		}
		return fs;
	}
}
