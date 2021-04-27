package Exceptions;

import Annotations.ViolatedConstraint;

@ViolatedConstraint(
		name="TRAVEL_LINE_UN", 
		msg="Linha com o código duplicado.")
public class TravelIsRegistredException extends RuntimeException {
	private static final long serialVersionUID = 4;
	private int code;
	
	
	public TravelIsRegistredException(String msg)
	{
		super(msg);
	}
	
	public TravelIsRegistredException(String msg, int errorCode)
	{
		super(msg);
		this.code = errorCode;
	}
	
	public int getErrorCode() 
	{
		return this.code;
	}
}
