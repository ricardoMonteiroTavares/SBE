package Interceptors;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import Annotations.Execute;
import Annotations.GetList;
import DAOs.Implements.DAOImpl;
import Util.JPAUtil;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class DAOIntercept implements MethodInterceptor {
	/*
	 * Parametros:
	 * 
	 * objeto - "this", o objeto "enhanced", isto é, o proxy.
	 * 
	 * metodo - o método interceptado, isto é, um método da classe
	 * ProdutoDAO, LanceDAO, etc.
	 * 
	 * args - um array de args; tipos primitivos são empacotados. Contém os
	 * argumentos que o método interceptado recebeu.
	 * 
	 * metodoProxy - utilizado para executar um método super. Classes geradas pela
	 * classe Enhancer passam este objeto para o objeto MethodInterceptor registrado
	 * quando um método interceptado é executado. Ele pode ser utilizado para
	 * invocar o método original, ou chamar o mesmo método sobre um objeto diferente
	 * do mesmo tipo. Trata-se de um objeto da classe
	 * net.sf.cglib.proxy.MethodProxy.
	 * 
	 */

	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) 
		throws Throwable {

		System.out.println("Método interceptado do DAO: " + method.getName() + 
				           " da classe " + method.getDeclaringClass().getName());

		Field field = obj.getClass().getSuperclass().getSuperclass().getDeclaredField("em");
		try {
			field.setAccessible(true);
			System.out.println(">>>>>>>>>>>>>>>> Injetou o entity manager.");
			field.set(obj, JPAUtil.getEntityManager());
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		DAOImpl<?, ?> dao = (DAOImpl<?, ?>) obj;

		if (method.isAnnotationPresent(Execute.class)) {
			return proxy.invokeSuper(obj, args);
		} else if (method.isAnnotationPresent(GetList.class)) {
			return dao.queryList(method, args);
		} else {
			throw new RuntimeException("Executando o método " + method.getName() + 
					                   " da classe " + method.getDeclaringClass() + 
					                   " que não é final e não está anotado.");
		}
	}
}
