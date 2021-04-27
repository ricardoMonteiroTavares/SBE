package Exceptions;

public class UnknownViolatedConstraintException extends RuntimeException {
	private static final long serialVersionUID = 5;
	private int code;
	
	
	public UnknownViolatedConstraintException(String msg)
	{
		super(msg);
	}
	
	public UnknownViolatedConstraintException(String msg, int errorCode)
	{
		super(msg);
		this.code = errorCode;
	}
	
	public int getErrorCode() 
	{
		return this.code;
	}

}
