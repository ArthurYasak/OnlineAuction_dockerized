package com.arthuryasak.dao;

import com.arthuryasak.models.Bet;
import com.arthuryasak.models.User;

import java.util.List;

public interface BetDAO {
    Bet getById(Integer betId);
    List<Bet> getByUser(User user);
    Bet getByLotId(int lotId);
    void add(Bet bet);
    Bet update(Bet bet);
    void deleteByLotId(int lotId);
    void deleteByUserId(int userId);
}
