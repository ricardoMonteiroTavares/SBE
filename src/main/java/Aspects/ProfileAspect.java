package Aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import Annotations.Profile;
import Exceptions.ProfileException;
import Singletons.ProfileSingleton;

@Aspect
public class ProfileAspect {
		
	@Pointcut("call(* Services.Interfaces..*.*(..))")
	public void detectProfile() {}
	
	@Around("detectProfile()")
	public Object detectProfileEx(ProceedingJoinPoint joinPoint) throws Throwable 
	{
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String[] loggedProfiles = ProfileSingleton.getSingleton().getProfiles();
		
		if(signature.getMethod().isAnnotationPresent(Profile.class)) 
		{
			String[] profiles = signature.getMethod().getAnnotation(Profile.class).profiles();
			
			for(String loggedProfile : loggedProfiles) 
			{
				for(String profile : profiles)
				{
					if(profile.equals(loggedProfile)) 
					{
						return joinPoint.proceed();
					}
				}
			}
			
			throw new ProfileException(signature.getDeclaringTypeName(), signature.getMethod().getName());
		}
		else 
		{
			return joinPoint.proceed();
		}
		
	}
		
	
}
