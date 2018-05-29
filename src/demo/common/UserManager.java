package demo.common;


import demo.Entity.SessionInfo;

public class UserManager {
	/**
	 * 当前用户线程变量
	 */
	private static ThreadLocal<SessionInfo> userVariable = new ThreadLocal<SessionInfo>();

	static public void setSessionInfo(SessionInfo user){
		userVariable.set(user);
	}
	static public SessionInfo getSessionInfo(){
		return userVariable.get();
	}
}
