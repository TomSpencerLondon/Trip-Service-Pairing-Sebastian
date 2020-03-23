package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

  private TripService tripService;
  private User loggedInUser;

  @Mock User userToCheck;
  @Mock User ANY_USER;
  @Mock TripDAO tripDAO;

  private List<Trip> tripList;

  @BeforeEach
  void setUp() {
    loggedInUser = ANY_USER;
    tripService = new TripService(tripDAO);
  }

  @Test
  void should_throw_an_exception_when_user_is_not_logged_in() {
    assertThrows(UserNotLoggedInException.class, () -> {
      tripService.getTripsByUser(userToCheck, null);
    });
  }

  @Test
  void return_an_empty_tripList_for_user_with_no_friends() {
    tripList = Collections.emptyList();

    assertEquals(tripList, tripService.getTripsByUser(userToCheck, ANY_USER));
  }

  @Test
  void return_empty_list_if_user_has_friend_not_logged_in_user() {
    when(userToCheck.isFriendsWith(loggedInUser)).thenReturn(false);
    tripList = Collections.emptyList();

    assertEquals(tripList, tripService.getTripsByUser(userToCheck, ANY_USER));
  }

  @Test
  void return_empty_trip_list_if_friend_but_no_trip() {
    when(userToCheck.isFriendsWith(loggedInUser)).thenReturn(true);
    tripList = Collections.emptyList();
    when(tripDAO.instanceFindTripsByUser(userToCheck)).thenReturn(tripList);

    assertEquals(tripList, tripService.getTripsByUser(userToCheck, ANY_USER));
  }

  @Test
  void returns_trips_for_friend_of_logged_in_user() {
    when(userToCheck.isFriendsWith(loggedInUser)).thenReturn(true);
    tripList = List.of(new Trip());
    when(tripDAO.instanceFindTripsByUser(userToCheck)).thenReturn(tripList);

    assertEquals(tripList, tripService.getTripsByUser(userToCheck, ANY_USER));
  }
}
