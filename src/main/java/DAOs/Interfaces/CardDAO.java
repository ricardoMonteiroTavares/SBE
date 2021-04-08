package DAOs.Interfaces;

import java.util.List;

import Annotations.GetList;
import Entities.Card;

public interface CardDAO extends DAO<Card, Long>{
	
	@GetList
	List<Card> getAllCards();
}
