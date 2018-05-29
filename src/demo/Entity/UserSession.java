package demo.Entity;


public class UserSession extends SessionInfo {
	private static final long serialVersionUID = 1246909218059581341L;
	private int uid;					//用户主键
	private String userid;				//用户id
	private String username;			//用户名称
	
	private int bid;					//部门主键
	private String branchid;			//部门id
	private String branchname;			//部门名称
	
	private int lft;					//部门左树节点		这玩意主要用来控制数据范围
	private int rgt;					//部门右树节点
	
	private int rid;
	private String rolename;
	private int level;
	
	
	

	public UserSession(UserEntity user){
		this.uid = user.getId();
		this.userid = user.getUserid();
		this.username = user.getName();
		
		BranchEntity branch = user.getBranch();
		this.bid = branch.getId();
		this.branchid = branch.getBranchid();
		this.branchname = branch.getName();
		this.lft = branch.getLft();
		this.rgt = branch.getRgt();
		
		RoleEntity role = user.getRole();
		this.rid = role.getId();
		this.rolename = role.getName();
		this.level = role.getLevel();
		for(RoleFunctionMap roleFunctionMap:role.getRoleFunctionMaps()){
			this.getPermission().put(roleFunctionMap.getFkfunction(),roleFunctionMap.getPurview());
		}
		
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getBranchid() {
		return branchid;
	}
	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public int getLft() {
		return lft;
	}
	public void setLft(int lft) {
		this.lft = lft;
	}
	public int getRgt() {
		return rgt;
	}
	public void setRgt(int rgt) {
		this.rgt = rgt;
	}
	
}
