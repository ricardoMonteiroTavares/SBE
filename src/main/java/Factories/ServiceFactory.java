package Factories;

import java.lang.reflect.Field;
import java.util.Set;

import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;

import Interceptors.ServiceInterceptor;
import net.sf.cglib.proxy.Enhancer;

public class ServiceFactory {
	@SuppressWarnings("unchecked")
	public static <T> T getService(Class<T> type) 
	{

		Reflections reflections = new Reflections("Services.Implements");

		Set<Class<? extends T>> classes = reflections.getSubTypesOf(type);

		if (classes.size() > 1)
			throw new RuntimeException("Somente uma classe pode implementar " + type.getName());

		Class<?> classe = classes.iterator().next();

		T service = (T) Enhancer.create(classe, new ServiceInterceptor());

		Field[] fields = classe.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Autowired.class)) {
				field.setAccessible(true);
				try {
					field.set(service, DAOFactory.getDAO(field.getType()));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			}
		}

		return service;
	}
}
