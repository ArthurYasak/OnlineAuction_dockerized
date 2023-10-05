package com.arthuryasak.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import com.arthuryasak.dao.LotDAOImpl;
import com.arthuryasak.dto.LotForm;
import com.arthuryasak.models.Lot;
import com.arthuryasak.models.LotProperty;
import com.arthuryasak.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LotServiceTest {
    @InjectMocks
    LotService lotService;

    @Mock
    LotDAOImpl lotDAO;

    Lot lot;
    LotProperty lotProperty;
    LotForm lotForm;

    byte[] byteFile;

    @BeforeEach
    void setup() {
        lotProperty = LotProperty.builder()
                .weight(7)
                .size(5)
                .description("simple lot")
                .build();

        lot = Lot.builder()
                .property(lotProperty)
                .minPrice(4500.0)
                .currentPrice(4500.0)
                .isBought(false)
                .build();

        lotForm = LotForm.builder()
                .weight(8)
                .size(8)
                .description("from form")
                .sellUntil(LocalDateTime.of(2023, Month.AUGUST, 18, 18, 00))
                .minPrice(100.0)
                .build();
        byteFile = new byte[]{1, 3, 6, 9};
    }

    @DisplayName("JUnit test for findAll method")
    @Test
    void findAllTest() {
        List<Lot> list = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            list.add(new Lot());
        }
        when(lotDAO.getAll()).thenReturn(list);

        List<Lot> testList = lotService.findAll();

        verify(lotDAO, times(1)).getAll();
        assertEquals(3, testList.size());
        assertSame(list, testList);
    }

    @DisplayName("JUnit test for findAllForUser method")
    @Test
    void findAllForUserTest() {
        List<Lot> list = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            list.add(new Lot());
        }
        when(lotDAO.getAllForUser(new User())).thenReturn(list);

        List<Lot> testList = lotService.findAllForUser(new User());

        verify(lotDAO, times(1)).getAllForUser(new User());
        assertEquals(3, testList.size());
        assertSame(list, testList);
    }

    @DisplayName("JUnit test for findSold method")
    @Test
    void findSoldTest() {
        List<Lot> list = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            list.add(new Lot());
        }
        when(lotDAO.getSold(new User())).thenReturn(list);

        List<Lot> testList = lotService.findSold(new User());

        verify(lotDAO, times(1)).getSold(new User());
        assertEquals(3, testList.size());
        assertSame(list, testList);
    }

    @DisplayName("JUnit test for findBought method")
    @Test
    void findBoughtTest() {
        List<Lot> list = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            list.add(new Lot());
        }
        when(lotDAO.getBought(new User())).thenReturn(list);

        List<Lot> testList = lotService.findBought(new User());

        verify(lotDAO, times(1)).getBought(new User());
        assertEquals(3, testList.size());
        assertSame(list, testList);
    }

    @DisplayName("JUnit test for findById method")
    @Test
    void findByIdTest() {
        when(lotDAO.getById(1)).thenReturn(lot);

        Lot testLot = lotService.findById(1);
        assertSame(lot, testLot);
    }

    @DisplayName("JUnit test for findByUserId method")
    @Test
    void findByUserIdTest() {
        List<Lot> list = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            list.add(new Lot());
        }
        when(lotDAO.getByUserId(1)).thenReturn(list);

        List<Lot> testList = lotService.findByUserId(1);

        verify(lotDAO, times(1)).getByUserId(1);
        assertEquals(3, testList.size());
        assertSame(list, testList);
    }

    @DisplayName("JUnit test for findWhereLastCustomer method")
    @Test
    void findWhereLastCustomerTest() {
        List<Lot> list = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            list.add(new Lot());
        }
        when(lotDAO.getByUserId(1)).thenReturn(list);

        List<Lot> testList = lotService.findByUserId(1);

        verify(lotDAO, times(1)).getByUserId(1);
        assertEquals(3, testList.size());
        assertSame(list, testList);
    }

    @DisplayName("JUnit test for findByNameOrType method")
    @Test
    void findByNameOrTypeTest() {
        List<Lot> list = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            list.add(new Lot());
        }
        when(lotDAO.getByNameOrTypeForUser("Telephone", new User())).thenReturn(list);

        List<Lot> testList = lotService.findByNameOrTypeForUser("Telephone", new User());

        verify(lotDAO, times(1)).getByNameOrTypeForUser("Telephone", new User());
        assertEquals(3, testList.size());
        assertSame(list, testList);
    }

    @DisplayName("JUnit test for add method")
    @Test
    void addTest() {
        User user = new User();
        lotService.add(lot, user);
        verify(lotDAO, times(1)).add(lot);
        assertSame(user, lot.getUserOwner());
    }

    @DisplayName("JUnit test for update(lot) method")
    @Test
    void updateWithLotParameterTest() {
        lot.setCurrentPrice(0.0);
        when(lotDAO.update(lot)).thenReturn(lot);

        Lot updLot = lotService.update(lot);
        verify(lotDAO,times(1)).update(lot);
        assertEquals(0.0, updLot.getCurrentPrice());
        assertSame(lot, updLot);
    }

    @DisplayName("JUnit test for update(lotId, lotForm) method")
    @Test
    void updateWithLotIdAndLotFormParametersTest() {
        when(lotDAO.update(lot)).thenReturn(lot);
        when(lotDAO.getById(1)).thenReturn(lot);
        Lot updLot = lotService.update(1, lotForm);
        verify(lotDAO,times(1)).update(lot);
        assertAll("Group assertion of lot",
                () -> assertEquals(4500, updLot.getCurrentPrice(), "Lot price must be 4500"),
                () -> assertEquals(8, updLot.getProperty().getWeight(), "Lot weight must be 8"),
                () -> assertEquals(8, updLot.getProperty().getSize(), "Lot size must be 8"),
                () -> assertEquals("from form", updLot.getProperty().getDescription(),
                        "Lot description must be 8"),
                () -> assertEquals(LocalDateTime.of(2023, Month.AUGUST, 18, 18, 00),
                        updLot.getSellUntil(), "Date de must be 18/08/2023 18:00"),
                () -> assertEquals(100.0, updLot.getMinPrice(), "Lot weight must be 8"),
                () -> assertSame(lot, updLot)
        );
    }

    @DisplayName("JUnit test for update(lotId, lotForm, byteFile) method")
    @Test
    void updateWithLotIdLotFormAndByteFileParametersTest() {
        when(lotDAO.update(lot)).thenReturn(lot);
        when(lotDAO.getById(1)).thenReturn(lot);
        Lot updLot = lotService.update(1, lotForm, byteFile);
        verify(lotDAO,times(1)).update(lot);
        assertAll("Group assertion of lot",
                () -> assertEquals(4500, updLot.getCurrentPrice(), "Lot price must be 4500"),
                () -> assertEquals(8, updLot.getProperty().getWeight(), "Lot weight must be 8"),
                () -> assertEquals(8, updLot.getProperty().getSize(), "Lot size must be 8"),
                () -> assertEquals("from form", updLot.getProperty().getDescription(),
                        "Lot description must be 8"),
                () -> assertEquals(LocalDateTime.of(2023, Month.AUGUST, 18, 18, 00),
                        updLot.getSellUntil(), "Date de must be 18/08/2023 18:00"),
                () -> assertEquals(100.0, updLot.getMinPrice(), "Lot weight must be 8"),
                () -> assertSame(byteFile, updLot.getProperty().getBytePhoto(),
                        "Must return equal byte[] array"),
                () -> assertSame(lot, updLot)
        );
    }

    @DisplayName("JUnit test for delete method")
    @Test
    void deleteTest() {
        lotService.delete(lot);
        verify(lotDAO,times(1)).delete(lot);
    }

    @DisplayName("JUnit test for deleteByUserId method")
    @Test
    void deleteByUserIdTest() {
        lotService.deleteByUserId(1);
        verify(lotDAO,times(1)).deleteByUserId(1);
    }

}
