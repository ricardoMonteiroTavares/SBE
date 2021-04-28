package Exceptions;

import Annotations.ViolatedConstraint;

@ViolatedConstraint(
		name="BOARDING_EXTRAVEL_FK", 
		msg="Este intinerário possui embarques e não pode ser removido.")
public class ExTravelWithBoardings extends RuntimeException {
	private static final long serialVersionUID = 3;
	private final String detailedMsg = "Foi tentado realizar uma operação de exclusão um ExecuteTravel, no banco de dados, porém o mesmo contém objetos Boardings referenciados.";
	
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
