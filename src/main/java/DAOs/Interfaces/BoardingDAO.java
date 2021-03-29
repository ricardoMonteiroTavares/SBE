package DAOs.Interfaces;

import java.util.Date;
import java.util.List;

import Entities.Boarding;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;

public interface BoardingDAO {
	Long insert(Boarding boarding);
	
	void update(Boarding boarding)
		throws ObjectNotFoundException, ObjectVersionException;
	
	void delete(Long id)
		throws ObjectNotFoundException;
	
	List<Boarding> getAllBoardingsByDate(Long cardCode, Date date)
			throws ObjectNotFoundException;
	
	List<Boarding> getAllBoardingsByPeriod(Long cardCode, Date start, Date finish)
			throws ObjectNotFoundException;
}
