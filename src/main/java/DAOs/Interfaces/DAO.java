package DAOs.Interfaces;

import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;

public interface DAO<T> {
	Long insert(T t);
	
	void update(T t)
		throws ObjectNotFoundException, ObjectVersionException;
	
	void delete(Long id)
		throws ObjectNotFoundException;
	
	T get(Long id)
		throws ObjectNotFoundException;
}
