package Exceptions;

public class ObjectNotFoundException extends Exception{

	private static final long serialVersionUID = 1;
	private int code;
	private final String detailedMsg = "O objeto da classe X e com código Y, não está presente no banco de dados.";
	
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
	
	@Override
	public String getMessage() 
	{
		return detailedMsg;
	}

}
