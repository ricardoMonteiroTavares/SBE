package DAOs.Interfaces;

import Entities.Card;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;

import java.util.List;

public interface CardDAO {
	Long insert(Card card);
	
	void update(Card card)
		throws ObjectNotFoundException, ObjectVersionException;
	
	void delete(Long code)
		throws ObjectNotFoundException;
	
	Card getCard(Long code)
		throws ObjectNotFoundException;
	
	List<Card> getAllCards();
}
