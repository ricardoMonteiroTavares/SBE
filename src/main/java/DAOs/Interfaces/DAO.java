package DAOs.Interfaces;

import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;

public interface DAO<T, PK> {
	Long insert(T t);
	
	void update(T t)
		throws ObjectNotFoundException, ObjectVersionException;
	
	void delete(PK id)
		throws ObjectNotFoundException;
	
	T get(PK id)
		throws ObjectNotFoundException;
}
