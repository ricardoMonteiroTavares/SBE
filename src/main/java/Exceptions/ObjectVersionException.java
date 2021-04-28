package Exceptions;

public class ObjectVersionException extends Exception {
	private static final long serialVersionUID = 2;
	private int code;
	private final String detailedMsg = "Foi tentado realizar uma opera��o de atualiza��o em um objeto de classe X e com id Y no banco de dados, por�m a vers�o do objeto, encontra-se desatualizado.";
	
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
	
	@Override
	public String getMessage() 
	{
		return detailedMsg;
	}
}
