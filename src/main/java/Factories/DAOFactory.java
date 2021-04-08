package Factories;

import java.util.Set;

import org.reflections.Reflections;

import Interceptors.DAOIntercept;
import net.sf.cglib.proxy.Enhancer;

public class DAOFactory {

	@SuppressWarnings("unchecked")
	public static <T> T getDAO(Class<T> type)
	{			
		Reflections reflections = new Reflections("DAOs.Implements");

		Set<Class<? extends T>> classes = reflections.getSubTypesOf(type);

		if (classes.size() > 1)
			throw new RuntimeException("Somente uma classe pode implementar " + type.getName());

		Class<?> classe = classes.iterator().next();

		return (T) Enhancer.create(classe, new DAOIntercept());
	}
}
