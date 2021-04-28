package Aspects;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogErrorsAspect {
	private static Logger logger = null;
	
	@Pointcut("call(* Services.Interfaces..*.*(..))")
	public void logError() {}
	
	@Around("logError()")
	public Object logErrorEx(ProceedingJoinPoint joinPoint) throws Throwable 
	{
		String method = joinPoint.getSignature().getClass().getName() + ":" + joinPoint.getSignature().getName();
		
		try
		{
			System.out.println("Executar Método: " + method);
			return joinPoint.proceed();
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
