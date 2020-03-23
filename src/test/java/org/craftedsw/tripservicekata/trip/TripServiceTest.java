package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

  private TripService tripService;
  private User loggedInUser;

  @Mock User userToCheck;
  @Mock User ANY_USER;
  private List<User> friendList;
  private List<Trip> tripList;

  @BeforeEach
  void setUp() {
    loggedInUser = ANY_USER;
    tripService = new TestableTripService();
  }

  @Test
  void should_throw_an_exception_when_user_is_not_logged_in() {
    loggedInUser = null;

    assertThrows(UserNotLoggedInException.class, () -> {
      tripService.getTripsByUser(null);
    });
  }

  @Test
  void return_an_empty_tripList_for_user_with_no_friends() {
    tripList = Collections.emptyList();

    assertEquals(tripList, tripService.getTripsByUser(new User()));
  }

  @Test
  void return_empty_list_if_user_has_friend_not_logged_in_user() {
    User friend = mock(User.class);
    friendList = Arrays.asList(friend);
    when(userToCheck.getFriends()).thenReturn(friendList);
    tripList = Collections.emptyList();

    assertEquals(tripList, tripService.getTripsByUser(userToCheck));
  }

  @Test
  void return_empty_trip_list_if_friend_but_no_trip() {
    friendList = Arrays.asList(loggedInUser);
    when(userToCheck.getFriends()).thenReturn(friendList);
    tripList = Collections.emptyList();
    when(userToCheck.trips()).thenReturn(tripList);

    assertEquals(tripList, tripService.getTripsByUser(userToCheck));
  }

  @Test
  void returns_trips_for_friend_of_logged_in_user() {
    friendList = Arrays.asList(loggedInUser);
    when(userToCheck.getFriends()).thenReturn(friendList);
    tripList = List.of(new Trip());
    when(userToCheck.trips()).thenReturn(tripList);

    assertEquals(tripList, tripService.getTripsByUser(userToCheck));
  }

  private class TestableTripService extends TripService {
    @Override
    protected User getLoggedInUser() {
      return loggedInUser;
    }

    @Override
    protected List<Trip> getTrips(User user){
      return userToCheck.trips();
    }
  }
}
