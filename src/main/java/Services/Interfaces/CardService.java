package Services.Interfaces;

import java.util.List;

import Entities.Card;
import Exceptions.ObjectNotFoundException;
import Exceptions.ObjectVersionException;

public interface CardService {
	Card insert(Card card);

	void update(Card card)
		throws ObjectNotFoundException;

	void delete(Card card)
		throws ObjectNotFoundException;

	Card get(Long code)
		throws ObjectNotFoundException;

	List<Card> getAllCards();
}
