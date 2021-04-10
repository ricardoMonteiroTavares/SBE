package DAOs.Implements;

import org.springframework.stereotype.Repository;

import DAOs.Interfaces.BoardingDAO;
import Entities.Boarding;

@Repository
public abstract class BoardingDAOImpl extends DAOImpl<Boarding, Long> implements BoardingDAO {
	
	public BoardingDAOImpl() {
		super(Boarding.class);
	}

}
