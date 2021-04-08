package Service.Interfaces;

import java.util.List;

import Entities.Card;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;

public interface CardService {
	Card insert(Card card);

	void update(Card card)
		throws ObjectNotFoundException, ObjectVersionException;

	void delete(Card card)
		throws ObjectNotFoundException;

	Card getCard(Long code)
		throws ObjectNotFoundException;

	List<Card> getAllCards();
}