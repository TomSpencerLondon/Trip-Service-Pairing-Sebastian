package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TripServiceTest {

  private TripService tripService;

  @Test
  void should_throw_on_exception_when_user_is_not_logged_in() {

    tripService = new TestableTripService();
    assertThrows(UserNotLoggedInException.class, () -> {
      tripService.getTripsByUser(null);
    });
  }


  private class TestableTripService extends TripService {
    @Override
    protected User getLoggedInUser() {
      return null;
    }
  }
}
