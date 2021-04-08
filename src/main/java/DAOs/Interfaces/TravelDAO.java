package DAOs.Interfaces;

import java.util.List;

import Annotations.GetList;
import Entities.Travel;

public interface TravelDAO extends DAO<Travel, Long>{
	
	@GetList
	List<Travel> getAllTravels();
}
