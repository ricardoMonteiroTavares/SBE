package Services.Interfaces;

import java.util.List;

import Annotations.Profile;
import Entities.Travel;
import Exceptions.ObjectNotFoundException;

public interface TravelService {
	@Profile(profiles={"admin"})
	Travel insert(Travel travel);

	@Profile(profiles={"admin"})
	void update(Travel travel)
		throws ObjectNotFoundException;

	@Profile(profiles={"admin"})
	void delete(Travel travel)
		throws ObjectNotFoundException;

	@Profile(profiles={"admin", "user"})
	Travel get(Long id)
		throws ObjectNotFoundException;

	@Profile(profiles={"admin", "user"})
	List<Travel> getAllTravels();
}
