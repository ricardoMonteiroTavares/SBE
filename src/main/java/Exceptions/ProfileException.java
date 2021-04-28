package Exceptions;

public class ProfileException extends RuntimeException {
	private static final long serialVersionUID = 6;
	private final String detailedMsg = "Foi tentado realizar uma operação X no banco de dados ao qual o(s) perfil(s) logado(s) não possui(em) autorização.";
	
	public ProfileException(String msg)
	{
		super(msg);
	}
	
	@Override
	public String getMessage() 
	{
		return detailedMsg;
	}

}
