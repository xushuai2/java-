package demo.Entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class RoleEntity extends BaseEntity {
	private static final long serialVersionUID = -1299373614923777515L;
	private int id;
	private String name;
	private int status;
	private String remark;
	private int level;
	private List<RoleFunctionMap> roleFunctionMaps = new ArrayList<RoleFunctionMap>();
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@JsonIgnore
	public List<RoleFunctionMap> getRoleFunctionMaps() {
		return roleFunctionMaps;
	}

	public void setRoleFunctionMaps(List<RoleFunctionMap> roleFunctionMaps) {
		this.roleFunctionMaps = roleFunctionMaps;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
