package demo.Entity;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BranchEntity extends BaseEntity {
	private static final long serialVersionUID = 3495896852312302227L;
	private Integer 		id;
	private String 			branchid;
	private String 			name;
	private Integer 		status;
	private String			remark;
	private Integer 		lft;
	private Integer	        rgt;
	private Integer			fkparent;
	private BranchEntity	parent;
	private List<BranchEntity> 	children = new ArrayList<BranchEntity>();
	public BranchEntity(){}
	public BranchEntity(int id){this.id = id;}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getBranchid() {
		return branchid;
	}
	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getFkparent() {
		return fkparent;
	}
	public void setFkparent(Integer fkparent) {
		this.fkparent = fkparent;
	}
	
	public BranchEntity getParent() {
		return parent;
	}
	public void setParent(BranchEntity parent) {
		this.parent = parent;
	}

	@JsonIgnore
	public List<BranchEntity> getChildren() {
		return children;
	}
	public void setChildren(List<BranchEntity> children) {
		this.children = children;
	}
	@JsonIgnore
	public Integer getLft() {
		return lft;
	}
	public void setLft(Integer lft) {
		this.lft = lft;
	}
	@JsonIgnore
	public Integer getRgt() {
		return rgt;
	}
	public void setRgt(Integer rgt) {
		this.rgt = rgt;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
