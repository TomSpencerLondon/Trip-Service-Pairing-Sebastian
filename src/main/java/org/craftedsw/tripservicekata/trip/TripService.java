package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.SessionInterface;
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

        return areFriends(user, loggedUser) ?
				getTrips(user) :
				Collections.emptyList();
    }

	private boolean areFriends(User user, User loggedUser) {
		return user.getFriends().contains(loggedUser);
	}

	private List<Trip> getTrips(User user) {
        return tripDAOInterface.instanceFindTripsByUser(user);
    }

}
