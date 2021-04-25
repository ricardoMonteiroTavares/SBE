package Aspects;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

public aspect LogErrorsAspect {
	private static Logger logger = null;
	
	pointcut logError()	: call (* Services.Implements..*.*(..));
	
	Object around() throws Throwable : logError() 
	{
		String method = thisJoinPointStaticPart.getSignature().getClass().getName() + ":" +thisJoinPointStaticPart.getSignature().getName();
		
		try
		{
			System.out.println("Executar Método: " + method);
			return proceed();
		}
		catch(Throwable throwable)
		{
			/*
			if(throwable.getClass().isAnnotationPresent(ExcecaoDeAplicacao.class)) {
				throw throwable;
			}
			*/
			System.out.println("Erro no Método: " + method);
			
			String msg = "";
			Throwable t = throwable;
			
			do 
			{
				msg = msg + ((t.getMessage() != null)? "\nCausado por: " + t.getMessage() : "");
				t = t.getCause();
			}
			while(t != null);
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			
			throwable.printStackTrace(pw);
			
			String stackTrace = sw.toString();
			
			logger = Logger.getLogger(this.getClass().getName());
			
			logger.error(
					"Método: " + method + 
					"\nMensagem: " + msg +
					"\nStackTrace: " + stackTrace + "\n"
			);
			
			throw throwable;
		}
	}
}
