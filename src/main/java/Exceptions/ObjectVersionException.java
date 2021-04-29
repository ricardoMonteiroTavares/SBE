package Exceptions;

public class ObjectVersionException extends Exception {
	private static final long serialVersionUID = 2;
	private static final String templateMsg = "Foi tentado realizar uma operação de atualização em um objeto de classe %s no banco de dados, porém o mesmo objeto foi modificado por outro usuário.";
	
	public ObjectVersionException(String className)
	{
		super(String.format(templateMsg, className));
	}
	

}
