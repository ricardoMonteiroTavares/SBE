package Exceptions;

public class UnknownViolatedConstraintException extends RuntimeException {
	private static final long serialVersionUID = 5;
	private final String detailedMsg = "Uma constraint n�o identificada do banco de dados foi violada.";
	
	
	public UnknownViolatedConstraintException(String msg)
	{
		super(msg);
	}
	
	@Override
	public String getMessage() 
	{
		return detailedMsg;
	}
}
