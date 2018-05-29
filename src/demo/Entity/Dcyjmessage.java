package demo.Entity;

import java.io.Serializable;

public class Dcyjmessage implements Serializable{
	private static final long serialVersionUID = 217742271355062230L;
	private Integer id; 
	private Integer by1;
	private String by2;
	private String by3;
	private String name;
	private String email;
	private String gender;//性别
	private Integer sfyx;
	private String fcolor;
	private String fshape;
	private String date;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFcolor() {
		return fcolor;
	}
	public void setFcolor(String fcolor) {
		this.fcolor = fcolor;
	}
	public String getFshape() {
		return fshape;
	}
	public void setFshape(String fshape) {
		this.fshape = fshape;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getBy1() {
		return by1;
	}
	public void setBy1(Integer by1) {
		this.by1 = by1;
	}
	public String getBy2() {
		return by2;
	}
	public void setBy2(String by2) {
		this.by2 = by2;
	}
	public String getBy3() {
		return by3;
	}
	public void setBy3(String by3) {
		this.by3 = by3;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getSfyx() {
		return sfyx;
	}
	public void setSfyx(Integer sfyx) {
		this.sfyx = sfyx;
	}
}
