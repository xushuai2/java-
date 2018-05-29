package demo.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import demo.Entity.BranchEntity;

public class BranchUtils {
	/**
	  * parseBranch(将子部门排列整理成树返回)
	  * @param parent
	  * @param branchs 一定要按从大至小的排列
	  * @return
	  */
	static public List<BranchEntity> parseBranch(List<BranchEntity> branchs){
		List<BranchEntity> branchArray = new ArrayList<BranchEntity>();
		Map<Integer,BranchEntity> map = new HashMap<Integer,BranchEntity>(branchs.size());
		//遍历按key全部放入map
		for(BranchEntity b : branchs){
			map.put(b.getId(), b);
			b.getChildren().clear();
		}
		Iterator<Map.Entry<Integer, BranchEntity>> itor = map.entrySet().iterator();  
		while(itor.hasNext()){
			Map.Entry<Integer,BranchEntity> entry = itor.next();
			BranchEntity b = entry.getValue();
			BranchEntity p = b.getParent()==null?null:map.get(b.getParent().getId());		//找父对象
			if(p!=null && b.getId()!=p.getId()){
				p.getChildren().add(b);
			}else{
				branchArray.add(b);
			}
		}
		return branchArray;
	}
	/**
	  * getBranchTreeJsonData(得到部门树json数据)
	  * @param branchs 部门信息
	  * @return list
	  */
	static public List<Map<String,Object>> getBranchTreeJsonData(List<BranchEntity> branchs){
		List<Map<String,Object>> rst = new ArrayList<Map<String,Object>>();
		Iterator<BranchEntity> itor = branchs.iterator();
		while(itor.hasNext()){
			BranchEntity b = (BranchEntity)itor.next();
			Map<String,Object> o = new HashMap<String,Object>();
			o.put("id", b.getId());
			o.put("text", b.getName()+"["+b.getBranchid()+"]");
			if(b.getChildren().size()>0){
				o.put("children", getBranchTreeJsonData(b.getChildren()));
			}
			rst.add(o);
		}
		return rst;
	}
}
