package Exceptions;

public class UnknownViolatedConstraintException extends RuntimeException {
	private static final long serialVersionUID = 5;
	
	public UnknownViolatedConstraintException()
	{
		super("Uma constraint n�o identificada do banco de dados foi violada.");
	}

}
