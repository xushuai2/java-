package demo.dao;

import org.springframework.stereotype.Service;

import demo.Entity.Dcyjmessage;
import demo.Entity.TUser;
@Service("userDao")
public interface TUserMapper {
	public TUser  selectByPrimaryKey(int id);
	public void  update();
	
	
	public void  insert();
	public TUser selectTuser(TUser tuser);
	public int insertDC(Dcyjmessage d) ;
	public int  insertTUser(TUser tuser);
}
