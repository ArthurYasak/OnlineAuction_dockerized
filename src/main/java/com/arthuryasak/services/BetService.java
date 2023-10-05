package com.arthuryasak.services;

import com.arthuryasak.dao.BetDAOImpl;
import com.arthuryasak.models.Bet;
import com.arthuryasak.models.Lot;
import com.arthuryasak.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BetService {

    BetDAOImpl betDAO = new BetDAOImpl();

    public Bet findById(Integer id) {
        return betDAO.getById(id);
    }

    public List<Bet> findByUser(User user) {
        return betDAO.getByUser(user);
    }

    public Bet findByLotId(int lotId) {
        return betDAO.getByLotId(lotId);
    }

    public void add(Bet betToSave, User customer, Lot targetLot) {
        betToSave.setUserOwner(customer);
        betToSave.setLot(targetLot);

        betDAO.add(betToSave);
    }

    public Bet update(Bet betToUpdate, User customer, Bet bet) {
        betToUpdate.setUserOwner(customer);
        betToUpdate.setBetPrice(bet.getBetPrice());

        return betDAO.update(betToUpdate);
    }

    public void deleteByUserId(int userId) {
        betDAO.deleteByUserId(userId);
    }

    public void deleteByLotId(int lotId) {
        betDAO.deleteByLotId(lotId);
    }

}
