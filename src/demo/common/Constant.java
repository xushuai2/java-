package demo.common;
public interface Constant {
	public String functions = "functions";
	
	public String actions = "actions";
	
	public String sessioninfo = "sessioninfo";
	
	public String dataImportConfig = "dataImportConfig";
	
	//权限
	public Integer PERMISSION_NO = 0;				//无
	public Integer PERMISSION_SELF = 1;				//本人
	public Integer PERMISSION_BRANCH = 2;			//本部门
	public Integer PERMISSION_BRANCHANDCHILDREN=3;	//本部门及下级部门
	
	public Integer USERLOG_INFO = 1;
	public Integer USERLOG_ERROR = 0;
}
