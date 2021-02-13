package Exceptions;

public class CardNotFoundException extends Exception{

	private static final long serialVersionUID = 1;
	private int code;
	
	public CardNotFoundException(String msg)
	{
		super(msg);
	}
	
	public CardNotFoundException(String msg, int errorCode)
	{
		super(msg);
		this.code = errorCode;
	}
	
	public int getErrorCode() 
	{
		return this.code;
	}

}
