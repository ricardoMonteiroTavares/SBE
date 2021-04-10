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
	 * objeto - "this", o objeto "enhanced", isto �, o proxy.
	 * 
	 * metodo - o m�todo interceptado, isto �, um m�todo da classe
	 * ProdutoDAO, LanceDAO, etc.
	 * 
	 * args - um array de args; tipos primitivos s�o empacotados. Cont�m os
	 * argumentos que o m�todo interceptado recebeu.
	 * 
	 * metodoProxy - utilizado para executar um m�todo super. Classes geradas pela
	 * classe Enhancer passam este objeto para o objeto MethodInterceptor registrado
	 * quando um m�todo interceptado � executado. Ele pode ser utilizado para
	 * invocar o m�todo original, ou chamar o mesmo m�todo sobre um objeto diferente
	 * do mesmo tipo. Trata-se de um objeto da classe
	 * net.sf.cglib.proxy.MethodProxy.
	 * 
	 */

	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) 
		throws Throwable {

		System.out.println("M�todo interceptado do DAO: " + method.getName() + 
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
			throw new RuntimeException("Executando o m�todo " + method.getName() + 
					                   " da classe " + method.getDeclaringClass() + 
					                   " que n�o � final e n�o est� anotado.");
		}
	}
}
