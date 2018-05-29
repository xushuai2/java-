package demo.exception;

public class ServiceException extends BaseException {
	private static final long serialVersionUID = 5771902185136738900L;
	private Integer errorCode;
	public ServiceException(Integer errorCode) {
		super("错误未知,错误码:"+errorCode.toString());
		this.errorCode = errorCode;
	}
	public ServiceException(String message) {
		super(message);
		errorCode = new Integer(1);
	}
	public ServiceException(String message,Integer errorCode) {
		super(message);
		this.errorCode = errorCode;
	}
	public ServiceException(Throwable cause) {
		super(cause);
		errorCode = new Integer(1);
	}
	public ServiceException(Integer errorCode,Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
	}
	public ServiceException(String message,Throwable cause) {
		super(message, cause);
		errorCode = new Integer(1);
	}
	public ServiceException(String message,Integer errorCode,Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
}
