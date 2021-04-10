package DAOs.Implements;

import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;

import Annotations.Execute;
import DAOs.Interfaces.DAO;
import Exceptions.ObjectNotFoundException;


public class DAOImpl<T, PK> implements DAO<T, PK> {
	private Class<T> type;

	protected EntityManager em; 

	public DAOImpl(Class<T> type) {
		this.type = type;
	}

	@Execute
	public T insert(T o) {
		try {
			em.persist(o);
		} catch (RuntimeException e) {
			throw e;
		}

		return o;
	}

	@Execute
	public void update(T o) {
		try {
			em.merge(o);
		} catch (RuntimeException e) {
			throw e;
		}
	}

	@Execute
	public void delete(T o) {
		try {
			em.remove(o);
		} catch (RuntimeException e) {
			throw e;
		}
	}

	@Execute
	public T get(PK id) throws ObjectNotFoundException {
		T t = null;
		try {
			t = em.find(type, id);

			if (t == null) {
				throw new ObjectNotFoundException("Elemento não encontrado");
			}
		} catch (RuntimeException e) {
			throw e;
		}
		return t;
	}

	@Execute
	public T getByLockMode(PK id) throws ObjectNotFoundException {
		T t = null;
		try {
			t = em.find(type, id, LockModeType.PESSIMISTIC_WRITE);

			if (t == null) {
				throw new ObjectNotFoundException("Elemento não encontrado");
			}
		} catch (RuntimeException e) {
			throw e;
		}

		return t;
	}

	@SuppressWarnings("unchecked")
	public final List<T> queryList(Method method, Object[] args) {
		try {
			String search = convertMethodToString(method);
			Query namedQuery = em.createNamedQuery(search);

			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					Object arg = args[i];
					namedQuery.setParameter(i + 1, arg); 
				}
			}
			return (List<T>) namedQuery.getResultList();
		} catch (RuntimeException e) {
			throw e;
		}
	}

	private String convertMethodToString(Method metodo) {
		return type.getSimpleName() + "." + metodo.getName();
	}
}
