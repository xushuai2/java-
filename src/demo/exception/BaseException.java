package demo.exception;
import java.util.ArrayList;
import java.util.List;

public class BaseException extends Exception {
	private static final long serialVersionUID = 2319434029574010644L;
	private List<Object> args = new ArrayList<Object>();
	public BaseException(String message) {
		super(message);
	}
	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}
	public void addArg(Object obj){
		args.add(obj);
	}
	public List<Object> getArgs() {
		return args;
	}
	public void setArgs(List<Object> args) {
		this.args = args;
	}
}
