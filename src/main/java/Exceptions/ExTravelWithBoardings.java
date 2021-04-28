package Exceptions;

import Annotations.ViolatedConstraint;

@ViolatedConstraint(
		name="BOARDING_EXTRAVEL_FK", 
		msg="Este intiner�rio possui embarques e n�o pode ser removido.")
public class ExTravelWithBoardings extends RuntimeException {
	private static final long serialVersionUID = 3;
	private final String detailedMsg = "Foi tentado realizar uma opera��o de exclus�o um ExecuteTravel, no banco de dados, por�m o mesmo cont�m objetos Boardings referenciados.";
	
	public ExTravelWithBoardings(String msg)
	{
		super(msg);
	}
	
	@Override
	public String getMessage() 
	{
		return detailedMsg;
	}
}
