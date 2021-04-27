package Aspects;

import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.springframework.dao.DataIntegrityViolationException;

import Annotations.ViolatedConstraint;
import Exceptions.UnknownViolatedConstraintException;

public aspect ViolatedConstraintAspect {
	
	private static Map<String, Class<?>> map = new HashMap<String, Class<?>>();
	private static List<String> constraints;
	
	static {
		Reflections reflections = new Reflections("Exceptions");
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(ViolatedConstraint.class);
		
		for (Class<?> classe : classes) {
			String constraint = classe.getAnnotation(ViolatedConstraint.class).name();
			map.put(constraint, classe);
		}
		constraints = new ArrayList<String>(map.keySet());
	}
	
	pointcut exception() : call (* Services.Implements..*.*(..));
	
	Object around() throws Throwable : exception()
	{
		try {
			return proceed();
		} catch (Throwable e) {
			Throwable t = e;

			if (t instanceof DataIntegrityViolationException) {
				t = t.getCause();
				while (t != null && !(t instanceof SQLException)) {
					t = t.getCause();
				}

				String msg = (t != null && t.getMessage() != null) ? t.getMessage() : "";
				msg = msg.toUpperCase();

				for (String constraint : constraints) {
					if (msg.indexOf(constraint) != -1) {
						Class<?> classe = map.get(constraint);
						String message = classe.getAnnotation(ViolatedConstraint.class).msg();
						Constructor<?> constructor = classe.getConstructor(String.class);
						throw (Exception) constructor.newInstance(message);
					}
				}
				throw new UnknownViolatedConstraintException("A operação não pode ser realizada.");
			} else {
				throw e;
			}
		}
		
	}

}
