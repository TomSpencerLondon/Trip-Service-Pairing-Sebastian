package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.SessionInterface;
import org.craftedsw.tripservicekata.user.User;

import java.util.Collections;
import java.util.List;

public class TripService {

	private SessionInterface sessionInterface;
	private TripDAO tripDAOInterface;

	public TripService(SessionInterface sessionInterface, TripDAO tripDAOInterface) {
		this.sessionInterface = sessionInterface;
		this.tripDAOInterface = tripDAOInterface;
	}

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		User loggedUser = validateLoggedIn();

        return areFriends(user, loggedUser) ?
				getTrips(user) :
				Collections.emptyList();
    }

	private boolean areFriends(User user, User loggedUser) {
		return user.getFriends().contains(loggedUser);
	}

	private User validateLoggedIn() {
		User loggedUser = getLoggedInUser();
		if (loggedUser == null) {
			throw new UserNotLoggedInException();
		}

		return loggedUser;
	}

	private List<Trip> getTrips(User user) {
        return tripDAOInterface.instanceFindTripsByUser(user);
    }

    private User getLoggedInUser() {
        return sessionInterface.getLoggedUser();
    }

}
