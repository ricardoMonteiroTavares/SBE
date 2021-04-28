package Exceptions;

import Annotations.ViolatedConstraint;

@ViolatedConstraint(
		name="TRAVEL_LINE_UN", 
		msg="Linha com o código duplicado.")
public class TravelIsRegistredException extends RuntimeException {
	private static final long serialVersionUID = 4;
	private final String detailedMsg = "Foi tentado realizar uma operação de inclusão de um Travel no banco de dados, porém o código da linha, informado pelo objeto, já existe no banco de dados.";
	
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
