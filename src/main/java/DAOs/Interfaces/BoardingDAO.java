package DAOs.Interfaces;

import java.util.Date;
import java.util.List;

import Annotations.GetList;
import Entities.Boarding;

public interface BoardingDAO extends DAO<Boarding, Long>{
	
	@GetList
	List<Boarding> getAllBoardingsByDate(Long cardCode, Date date);
	
	@GetList
	List<Boarding> getAllBoardingsByPeriod(Long cardCode, Date start, Date finish);
}
