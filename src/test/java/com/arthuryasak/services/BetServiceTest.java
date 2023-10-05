package com.arthuryasak.services;

import com.arthuryasak.dao.BetDAOImpl;
import com.arthuryasak.models.Bet;
import com.arthuryasak.models.Lot;
import com.arthuryasak.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class BetServiceTest {

    @InjectMocks
    BetService betService;

    @Mock
    BetDAOImpl betDAO;

    Bet bet;

    @BeforeEach
    void setup() {
        bet = Bet.builder()
                .betPrice(8.0)
                .build();
    }

    @DisplayName("JUnit test for findById method")
    @Test
    void findByIdTest() {
        when(betDAO.getById(1)).thenReturn(bet);

        Bet testBet = betService.findById(1);
        verify(betDAO,times(1)).getById(1);
        assertSame(bet, testBet);
    }

    @DisplayName("JUnit test for findByUser method")
    @Test
    void findByUserTest() {
        List<Bet> list = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            list.add(new Bet());
        }
        when(betDAO.getByUser(new User())).thenReturn(list);

        List testList = betService.findByUser(new User());

        verify(betDAO, times(1)).getByUser(new User());
        assertEquals(3, testList.size());
        assertSame(list, testList);
    }

    @DisplayName("JUnit test for findByLotId method")
    @Test
    void findByLotIdTest() {
        when(betDAO.getByLotId(1)).thenReturn(bet);

        Bet testBet = betService.findByLotId(1);
        verify(betDAO,times(1)).getByLotId(1);
        assertSame(bet, testBet);
    }

    @DisplayName("JUnit test for add method")
    @Test
    void addTest() {
        User userOwner = new User();
        Lot targetLot = new Lot();
        betService.add(bet, userOwner, targetLot);

        verify(betDAO, times(1)).add(bet);
        assertEquals(userOwner, bet.getUserOwner());
        assertEquals(targetLot, bet.getLot());

    }

    @DisplayName("JUnit test for update method")
    @Test
    void updateTest() {
        User userCustomer = new User();
        Bet newBet = new Bet(18.00);
        when(betDAO.update(bet)).thenReturn(bet);

        Bet updBet = betService.update(bet, userCustomer, newBet);

        verify(betDAO, times(1)).update(bet);
        assertEquals(18.0, updBet.getBetPrice());
        assertEquals(userCustomer, updBet.getUserOwner());
    }

    @DisplayName("JUnit test for deleteByUserId method")
    @Test
    void deleteByUserIdTest() {
        betService.deleteByUserId(1);
        verify(betDAO, times(1)).deleteByUserId(1);
    }

    @DisplayName("JUnit test for deleteByLotId method")
    @Test
    void deleteByLotIdTest() {
        betService.deleteByLotId(1);
        verify(betDAO, times(1)).deleteByLotId(1);
    }
}
