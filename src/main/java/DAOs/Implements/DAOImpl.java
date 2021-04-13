package DAOs.Implements;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import DAOs.Interfaces.DAO;
import Exceptions.ObjectNotFoundException;


public class DAOImpl<T, PK extends Serializable> implements DAO<T, PK> {
	private Class<T> type;

	@PersistenceContext
	protected EntityManager em; 

	public DAOImpl(Class<T> type) 
	{
		this.type = type;
	}

	public T insert(T o) 
	{		
		em.persist(o);
		return o;
	}

	public void update(T o) 
	{		
		em.merge(o);		
	}

	public void delete(T o) 
	{		
		em.remove(o);
	}

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
