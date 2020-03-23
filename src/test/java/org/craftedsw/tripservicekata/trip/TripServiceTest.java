package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

  private TripService tripService;
  private User loggedInUser;

  @Mock User userToCheck;
  @Mock User ANY_USER;
  @Mock TripDAO tripDAO;

  private List<User> friendList;
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
    User friend = mock(User.class);
    friendList = Arrays.asList(friend);
    when(userToCheck.getFriends()).thenReturn(friendList);
    tripList = Collections.emptyList();

    assertEquals(tripList, tripService.getTripsByUser(userToCheck, ANY_USER));
  }

  @Test
  void return_empty_trip_list_if_friend_but_no_trip() {
    friendList = Arrays.asList(loggedInUser);
    when(userToCheck.getFriends()).thenReturn(friendList);
    tripList = Collections.emptyList();
    when(tripDAO.instanceFindTripsByUser(userToCheck)).thenReturn(tripList);

    assertEquals(tripList, tripService.getTripsByUser(userToCheck, ANY_USER));
  }

  @Test
  void returns_trips_for_friend_of_logged_in_user() {
    friendList = Arrays.asList(loggedInUser);
    when(userToCheck.getFriends()).thenReturn(friendList);
    tripList = List.of(new Trip());
    when(tripDAO.instanceFindTripsByUser(userToCheck)).thenReturn(tripList);

    assertEquals(tripList, tripService.getTripsByUser(userToCheck, ANY_USER));
  }
}
