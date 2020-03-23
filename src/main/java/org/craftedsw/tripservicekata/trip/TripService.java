package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;

import java.util.Collections;
import java.util.List;

public class TripService {

	private TripDAO tripDAOInterface;

	public TripService(TripDAO tripDAOInterface) {
		this.tripDAOInterface = tripDAOInterface;
	}

	public List<Trip> getTripsByUser(User user, User loggedUser) throws UserNotLoggedInException {
		if (loggedUser == null) {
			throw new UserNotLoggedInException();
		}

        return user.isFriendsWith(loggedUser) ?
				getTrips(user) :
				Collections.emptyList();
    }

	private List<Trip> getTrips(User user) {
        return tripDAOInterface.instanceFindTripsByUser(user);
    }

}
