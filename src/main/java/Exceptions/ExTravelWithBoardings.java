package Exceptions;

import Annotations.ViolatedConstraint;

@ViolatedConstraint(
		name="BOARDING_EXTRAVEL_FK", 
		msg="Este intinerário possui embarques e não pode ser removido.")
public class ExTravelWithBoardings extends RuntimeException {
	private static final long serialVersionUID = 3;
	private int code;
	
	
	public ExTravelWithBoardings(String msg)
	{
		super(msg);
	}
	
	public ExTravelWithBoardings(String msg, int errorCode)
	{
		super(msg);
		this.code = errorCode;
	}
	
	public int getErrorCode() 
	{
		return this.code;
	}
}
