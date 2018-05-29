package demo.Entity;
// default package

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Function entity. @author MyEclipse Persistence Tools
 */
public class FunctionEntity extends BaseEntity {
	private static final long serialVersionUID = -2612409173275317886L;
	private String id;
	private String fkparent;
	private FunctionEntity parent;
	private String name;
	private String url;
	private String whitelist;
	private int    menu;
	private int    pos;
	private String remark;
	private int		level;
	private List<FunctionEntity> 	children = new ArrayList<FunctionEntity>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public FunctionEntity getParent() {
		return parent;
	}
	public void setParent(FunctionEntity parent) {
		this.parent = parent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getMenu() {
		return menu;
	}
	public void setMenu(int menu) {
		this.menu = menu;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@JsonIgnore
	public List<FunctionEntity> getChildren() {
		return children;
	}
	public void setChildren(List<FunctionEntity> children) {
		this.children = children;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getWhitelist() {
		return whitelist;
	}
	public void setWhitelist(String whitelist) {
		this.whitelist = whitelist;
	}

	@JsonIgnore
	public String getFkparent() {
		return fkparent;
	}
	public void setFkparent(String fkparent) {
		this.fkparent = fkparent;
	}
}