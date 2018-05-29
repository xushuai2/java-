package demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class RoleFunctionMap extends BaseEntity {
	private static final long serialVersionUID = 9025420905433780850L;
	private int id;
	private Integer fkrole;
	private RoleEntity role;
	private String fkfunction;
	private int purview;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getFkfunction() {
		return fkfunction;
	}
	public void setFkfunction(String fkfunction) {
		this.fkfunction = fkfunction;
	}
	
	public int getPurview() {
		return purview;
	}
	public void setPurview(int purview) {
		this.purview = purview;
	}

	@JsonIgnore
	public RoleEntity getRole() {
		return role;
	}
	public void setRole(RoleEntity role) {
		this.role = role;
	}

	@JsonIgnore
	public Integer getFkrole() {
		return fkrole;
	}
	public void setFkrole(Integer fkrole) {
		this.fkrole = fkrole;
	}
}
