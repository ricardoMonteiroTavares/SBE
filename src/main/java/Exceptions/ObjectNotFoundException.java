package Exceptions;

public class ObjectNotFoundException extends Exception{

	private static final long serialVersionUID = 1;
	private int code;
	
	public ObjectNotFoundException(String msg)
	{
		super(msg);
	}
	
	public ObjectNotFoundException(String msg, int errorCode)
	{
		super(msg);
		this.code = errorCode;
	}
	
	public int getErrorCode() 
	{
		return this.code;
	}

}
