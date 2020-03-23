package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

import java.util.Collections;
import java.util.List;

public class TripService {

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

	protected List<Trip> getTrips(User user) {
        return TripDAO.findTripsByUser(user);
    }

    protected User getLoggedInUser() {
        return UserSession.getInstance().getLoggedUser();
    }

}
