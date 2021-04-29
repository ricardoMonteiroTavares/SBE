package DAOs.Implements;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import DAOs.Interfaces.DAO;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;


public class DAOImpl<T, PK extends Serializable> implements DAO<T, PK> {
	private Class<T> type;

	@PersistenceContext
	protected EntityManager em; 

	public DAOImpl(Class<T> type) 
	{
		this.type = type;
	}

	public final T insert(T o) 
	{		
		em.persist(o);
		return o;
	}

	public final void update(T o) throws ObjectVersionException
	{		
		try {
		em.merge(o);	
		}
		catch(OptimisticLockException e) {
			throw new ObjectVersionException(type.getName());
		}
	}

	public final void delete(T o) 
	{		
		em.remove(em.contains(o) ? o : em.merge(o));
	}

	public final T get(PK id) throws ObjectNotFoundException {
		T t = null;
		try {
			t = em.find(type, id);

			if (t == null) {
				throw new ObjectNotFoundException(type.getName(),id.toString());
			}
		} catch (RuntimeException e) {
			throw e;
		}
		return t;
	}

	public final T getByLockMode(PK id) throws ObjectNotFoundException {
		T t = null;
		try {
			t = em.find(type, id, LockModeType.PESSIMISTIC_WRITE);

			if (t == null) {
				throw new ObjectNotFoundException(type.getName(),id.toString());
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
