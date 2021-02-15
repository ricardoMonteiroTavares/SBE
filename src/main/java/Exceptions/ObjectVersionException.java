package Exceptions;

public class ObjectVersionException extends Exception {
	private static final long serialVersionUID = 2;
	private int code;
	
	
	public ObjectVersionException() {}
	
	public ObjectVersionException(String msg)
	{
		super(msg);
	}
	
	public ObjectVersionException(String msg, int errorCode)
	{
		super(msg);
		this.code = errorCode;
	}
	
	public int getErrorCode() 
	{
		return this.code;
	}

}
