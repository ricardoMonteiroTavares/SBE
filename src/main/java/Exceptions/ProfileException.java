package Exceptions;

public class ProfileException extends RuntimeException {
	private static final long serialVersionUID = 6;
	private static final String templateMsg = "Foi tentado realizar uma operação %s.%s no banco de dados ao qual o(s) perfil(s) logado(s) não possui(em) autorização.";
	
	
	public ProfileException(String className, String method)
	{			
		super(String.format(templateMsg, className, method));
	}

}
