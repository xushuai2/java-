package demo.Entity;

import java.io.Serializable;

public class TUser implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id; 
	private Integer status;
	private String name;
	private String password; 
	private String mobile;
	private String email;
	private String level;//会员等级
	private int credit;//积分
	private String zctime;
	private int age;
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getZctime() {
		return zctime;
	}
	public void setZctime(String zctime) {
		this.zctime = zctime;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public TUser() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	private TUser(Integer id, Integer status, String name, String password) {
		super();
		this.id = id;
		this.status = status;
		this.name = name;
		this.password = password;
	}
	@Override
	public String toString() {
		return "TUser [id=" + id + ", status=" + status + ", name=" + name + ", password=" + password + ", mobile="
				+ mobile + ", email=" + email + ", level=" + level + ", credit=" + credit + ", zctime=" + zctime
				+ ", age=" + age + "]";
	}
	
	
}
