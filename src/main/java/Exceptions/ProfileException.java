package Exceptions;

public class ProfileException extends RuntimeException {
	private static final long serialVersionUID = 6;
	private int code;
	
	
	public ProfileException(String msg)
	{
		super(msg);
	}
	
	public ProfileException(String msg, int errorCode)
	{
		super(msg);
		this.code = errorCode;
	}
	
	public int getErrorCode() 
	{
		return this.code;
	}

}
