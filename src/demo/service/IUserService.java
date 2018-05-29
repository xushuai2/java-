package demo.service;

import java.util.List;

import demo.Entity.AppStatistics;
import demo.Entity.Dcyjmessage;
import demo.Entity.TUser;

public interface IUserService {
	public TUser getUserById(int id);
	public void gettestUserById(int id);
	public void saveUser(TUser user);
	
	public TUser findUser(TUser user);
	public int saveDcyjmessage(Dcyjmessage dc);
	public int savetuser(TUser tuser);
	
	public int updatedxc(int id );
	
	public List<AppStatistics> selectAPP(String type);
	
}