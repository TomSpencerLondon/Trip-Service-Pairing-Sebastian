package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

  private TripService tripService;
  private User loggedInUser;
  @Mock
  User userToCheck;

  @Test
  void should_throw_an_exception_when_user_is_not_logged_in() {

    tripService = new TestableTripService();
    loggedInUser = null;
    assertThrows(UserNotLoggedInException.class, () -> {
      tripService.getTripsByUser(null);
    });
  }

  @Test
  void return_an_empty_tripList_for_user_with_no_friends() {

    tripService = new TestableTripService();
    loggedInUser = new User();
    ArrayList<Trip> tripList = new ArrayList<Trip>();
    assertEquals(tripList, tripService.getTripsByUser(new User()));
  }

  @Test
  void return_empty_list_if_user_has_friend_not_logged_in_user() {

    tripService = new TestableTripService();
    loggedInUser = new User();
    List<User> userList = Arrays.asList(new User());
    when(userToCheck.getFriends()).thenReturn(userList);

    ArrayList<Trip> tripList = new ArrayList<Trip>();
    assertEquals(tripList, tripService.getTripsByUser(userToCheck));
  }

  @Test
  void return_empty_trip_list_if_friend_but_no_trip() {
    tripService = new TestableTripService();
    loggedInUser = new User();
    List<User> userList = Arrays.asList(loggedInUser);
    when(userToCheck.getFriends()).thenReturn(userList);
    ArrayList<Trip> tripList = new ArrayList<Trip>();

    when(userToCheck.trips()).thenReturn(tripList);

    assertEquals(tripList, tripService.getTripsByUser(userToCheck));
  }

  @Test
  void returns_trips_for_friend_of_logged_in_user() {
    tripService = new TestableTripService();
    loggedInUser = new User();
    List<User> userList = Arrays.asList(loggedInUser);
    when(userToCheck.getFriends()).thenReturn(userList);
    List<Trip> tripList = List.of(new Trip());

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
