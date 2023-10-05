package com.arthuryasak.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


import com.arthuryasak.dao.UserDAOImpl;
import com.arthuryasak.models.AuthorizationData;
import com.arthuryasak.models.User;
import com.arthuryasak.models.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserDAOImpl userDAO;

    private User user;
    private UserData userData;
    private AuthorizationData authorizationData;

    @BeforeEach
    void setup() {
        userData = UserData.builder()
                .name("Arthur")
                .surname("Yasak")
                .age(26)
                .telephone("+7(926)664-18-09")
                .email("arthuryasak@yandex.ru")
                .address("53-strelkovaya divisia street")
                .build();

        authorizationData = AuthorizationData.builder()
                .login("Arthur")
                .password("123")
                .build();

        user = User.builder()
                .userId(1)
                .userData(userData)
                .authorizationData(authorizationData)
                .build();
    }

    @DisplayName("JUnit test for findById method")
    @Test
    void findByIdTest() {
        when(userDAO.getById(1)).thenReturn(user);

        User testUser = userService.findById(1);

        assertEquals(1, testUser.getUserId());
        assertEquals("Arthur", testUser.getAuthorizationData().getLogin());
        assertEquals("123", testUser.getAuthorizationData().getPassword());
        assertEquals("Arthur", testUser.getUserData().getName());
        assertEquals("Yasak", testUser.getUserData().getSurname());
        assertEquals(26, testUser.getUserData().getAge());
        assertEquals("+7(926)664-18-09", testUser.getUserData().getTelephone());
        assertEquals("arthuryasak@yandex.ru", testUser.getUserData().getEmail());
        assertEquals("53-strelkovaya divisia street", testUser.getUserData().getAddress());
        assertSame(user, testUser);
    }

    @DisplayName("JUnit test for findByUsername method")
    @Test
    void findByUsernameTest() {
        when(userDAO.getByUsername("Arthur")).thenReturn(user);

        User testUser = userService.findUserByUsername("Arthur");

        assertEquals(1, testUser.getUserId());
        assertEquals("Arthur", testUser.getAuthorizationData().getLogin());
        assertEquals("123", testUser.getAuthorizationData().getPassword());
        assertEquals("Arthur", testUser.getUserData().getName());
        assertEquals("Yasak", testUser.getUserData().getSurname());
        assertEquals(26, testUser.getUserData().getAge());
        assertEquals("+7(926)664-18-09", testUser.getUserData().getTelephone());
        assertEquals("arthuryasak@yandex.ru", testUser.getUserData().getEmail());
        assertEquals("53-strelkovaya divisia street", testUser.getUserData().getAddress());
        assertSame(user, testUser);
    }

    @DisplayName("JUnit test for saveUser method")
    @Test
    void saveUserTest() {
        userService.saveUser(user);
        verify(userDAO, times(1)).add(user);

        assertEquals("ROLE_USER", user.getUserRoles().stream().findFirst().get().getName());
    }

    @DisplayName("JUnit test for deleteById method")
    @Test
    void deleteByIdTest() {
        userService.deleteById(1, new ArrayList());
        verify(userDAO, times(1)).deleteById(1);
    }

    @DisplayName("JUnit test for updateUserTest method")
    @Test
    void updateUserTest() {
        user.getUserData().setAge(27);
        when(userDAO.update(user)).thenReturn(user);

        User updUser = userService.updateUser(user);
        verify(userDAO, times(1)).update(user);
        assertEquals(27, updUser.getUserData().getAge());
        assertSame(updUser, user);
    }

    @DisplayName("JUnit test for findAll method")
    @Test
    void findAllTest() {
        List<User> userList = new ArrayList<User>();
        for(int i = 0; i < 3; i++) {
            userList.add(new User());
        }
        when(userDAO.getAll(1)).thenReturn(userList);

        List<User> testUserList = userService.findAll(1);
        assertEquals(3, testUserList.size());
        verify(userDAO, times(1)).getAll(1);
        assertSame(userList, testUserList);
    }

    @DisplayName("JUnit test for deleteAll method")
    @Test
    void deleteAllTest() {
        userService.deleteAll();
        verify(userDAO,times(1)).deleteAll();
    }
}
