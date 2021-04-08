package Interceptors;

import java.lang.reflect.Method;

import org.springframework.transaction.annotation.Transactional;

import Util.JPAUtil;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ServiceInterceptor implements MethodInterceptor  {
	public Object intercept(Object object, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		try {
			if (method.isAnnotationPresent(Transactional.class)) {
				JPAUtil.beginTransaction();
			}

			System.out.println("Método interceptado do serviço: " + method.getName() + " da classe "
					+ method.getDeclaringClass().getName());			

			Object obj = proxy.invokeSuper(object, args);

			if (method.isAnnotationPresent(Transactional.class)) {
				JPAUtil.commitTransaction();
			}

			return obj;
		} catch (RuntimeException e) {
			if (method.isAnnotationPresent(Transactional.class)) {
				JPAUtil.rollbackTransaction();
			}
			throw e;
		} catch (Exception e) {
			if (method.isAnnotationPresent(Transactional.class)) {
				Class<?>[] classes = method.getAnnotation(Transactional.class).rollbackFor();
				boolean flag = false;
				for (Class<?> classe : classes) {
					if (classe.isInstance(e)) {
						flag = true;
						break;
					}
				}
				if (flag) {
					JPAUtil.rollbackTransaction();
				} else {
					JPAUtil.commitTransaction();
				}
			}
			throw e;
		} finally {
			JPAUtil.closeEntityManager();
		}
	}
}
