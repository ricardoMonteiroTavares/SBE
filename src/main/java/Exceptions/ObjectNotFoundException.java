package Exceptions;

public class ObjectNotFoundException extends Exception{

	private static final long serialVersionUID = 1;
	private static final String templateMsg = "O objeto da classe %s e com código %s, não está presente no banco de dados.";
	
	public ObjectNotFoundException(String className, String id)
	{
		super(String.format(templateMsg, className, id));
	}

}
