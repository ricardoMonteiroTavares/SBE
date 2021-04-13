package DAOs.Interfaces;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

import Exceptions.ObjectNotFoundException;

public interface DAO<T, PK extends Serializable> {
	T insert(T o);
	
	void update(T o);
	
	void delete(T o);
	
	T get(PK id)
		throws ObjectNotFoundException;
	
	T getByLockMode(PK id)
			throws ObjectNotFoundException;
	
	List<T> queryList(Method method, Object[] args) 
			throws ObjectNotFoundException;
}
