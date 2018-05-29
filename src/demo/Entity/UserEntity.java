package demo.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import demo.common.DateUtil;

public class UserEntity extends BaseEntity {
	private static final long serialVersionUID = -3343029951121575450L;
	private Integer id;
	private String userid;
	private String name;
	private String password;
	private String salt;
	private Integer status;
	private String remark;
	private Integer fkrole;
	private Integer fkbranch;
	private RoleEntity role;
	private BranchEntity branch;
	private Integer mpwd = 0;
	private String lltime;
	private String llipaddr;

	private Integer lcount = 0;
	private Integer lfcount = 0;
	private String lmpwdtime;

	public UserEntity() {
	}

	public UserEntity(int id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@JsonIgnore
	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	@JsonIgnore
	public BranchEntity getBranch() {
		return branch;
	}

	public void setBranch(BranchEntity branch) {
		this.branch = branch;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLltime() {
		return lltime;
	}

	public void setLltime(Date lltime) {
		this.lltime = DateUtil.getStringFromDate(lltime);
	}

	public String getLlipaddr() {
		return llipaddr;
	}

	public void setLlipaddr(String llipaddr) {
		this.llipaddr = llipaddr;
	}

	public Integer getMpwd() {
		return mpwd;
	}

	public void setMpwd(Integer mpwd) {
		this.mpwd = mpwd;
	}

	public Integer getFkrole() {
		return fkrole;
	}

	public void setFkrole(Integer fkrole) {
		this.fkrole = fkrole;
	}

	@JsonIgnore
	public Integer getFkbranch() {
		return fkbranch;
	}

	public void setFkbranch(Integer fkbranch) {
		this.fkbranch = fkbranch;
	}

	public Integer getLcount() {
		return lcount;
	}

	public void setLcount(Integer lcount) {
		this.lcount = lcount;
	}

	public Integer getLfcount() {
		return lfcount;
	}

	public void setLfcount(Integer lfcount) {
		this.lfcount = lfcount;
	}

	public String getLmpwdtime() {
		return lmpwdtime;
	}

	public void setLmpwdtime(Date lmpwdtime) {
		this.lmpwdtime = DateUtil.getStringFromDate(lmpwdtime);
	}
}
