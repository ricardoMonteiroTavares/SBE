package Exceptions;

import Annotations.ViolatedConstraint;

@ViolatedConstraint(
		name="TRAVEL_LINE_UN", 
		msg="Linha com o c�digo duplicado.")
public class TravelIsRegistredException extends RuntimeException {
	private static final long serialVersionUID = 4;
	private final String detailedMsg = "Foi tentado realizar uma opera��o de inclus�o de um Travel no banco de dados, por�m o c�digo da linha, informado pelo objeto, j� existe no banco de dados.";
	
	public TravelIsRegistredException(String msg)
	{
		super(msg);
	}
	
	@Override
	public String getMessage() 
	{
		return detailedMsg;
	}
}
