package DAOs.Implements;

import org.springframework.stereotype.Repository;

import DAOs.Interfaces.TravelDAO;
import Entities.Travel;

@Repository
public abstract class TravelDAOImpl extends DAOImpl<Travel, Long> implements TravelDAO {

	public TravelDAOImpl() {
		super(Travel.class);
	}
	
}
