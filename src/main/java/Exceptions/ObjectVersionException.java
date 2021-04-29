package Exceptions;

public class ObjectVersionException extends Exception {
	private static final long serialVersionUID = 2;
	private static final String templateMsg = "Foi tentado realizar uma opera��o de atualiza��o em um objeto de classe %s no banco de dados, por�m o mesmo objeto foi modificado por outro usu�rio.";
	
	public ObjectVersionException(String className)
	{
		super(String.format(templateMsg, className));
	}
	

}
