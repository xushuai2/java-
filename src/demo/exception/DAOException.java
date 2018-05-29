package demo.exception;


public class DAOException extends BaseException {
	private static final long serialVersionUID = -4282159843809548504L;

	public DAOException(String message) {
		super(message);
	}
	public DAOException(Throwable cause) {
		super(cause);
	}
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}
}
