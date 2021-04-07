package DAOs.Interfaces;

import java.lang.reflect.Method;
import java.util.List;

import Exceptions.ObjectNotFoundException;

public interface DAO<T, PK> {
	T insert(T o);
	
	void update(T o)
		throws ObjectNotFoundException;
	
	void delete(T o)
		throws ObjectNotFoundException;
	
	T get(PK id)
		throws ObjectNotFoundException;
	
	T getByLockMode(PK id)
			throws ObjectNotFoundException;
	
	T query(Method method, Object[] args) 
			throws ObjectNotFoundException;
	
	List<T> queryList(Method method, Object[] args) 
			throws ObjectNotFoundException;
}
