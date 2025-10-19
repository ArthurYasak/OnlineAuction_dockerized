package com.arthuryasak.services;

import com.arthuryasak.dao.LotDAO;
import com.arthuryasak.dao.LotDAOImpl;
import com.arthuryasak.models.Lot;
import com.arthuryasak.dto.LotForm;
import com.arthuryasak.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LotService {

    private final LotDAO lotDAO;

    public List<Lot> findAll() {
        return lotDAO.getAll();
    }

    public List<Lot> findAllForUser(User currentUser) {
        return lotDAO.getAllForUser(currentUser);
    }

    public List<Lot> findSold(User currentUser) {
        return lotDAO.getSold(currentUser);
    }

    public List<Lot> findBought(User currentUser) {
        return lotDAO.getBought(currentUser);
    }

    public Lot findById(Integer id) {
        return lotDAO.getById(id);
    }

    public List<Lot> findByUserId(int userId) {
        return lotDAO.getByUserId(userId);
    }

    public List<Lot> findWhereLastCustomer(int userId){
        return lotDAO.getWhereLastCustomer(userId);
    }


    public List<Lot> findByNameOrTypeForUser(String keyWord, User currentUser) {
        return lotDAO.getByNameOrTypeForUser(keyWord, currentUser);
    }

    public void add(Lot lot, User user) {
        lot.setUserOwner(user);
        lotDAO.add(lot);
    }

    public Lot update(Lot lot) {
        return lotDAO.update(lot);
    }
    public Lot update(int lotId, LotForm lotForm) {
        Lot lotToUpdate = findById(lotId);
        lotToUpdate.getProperty().setWeight(lotForm.getWeight());
        lotToUpdate.getProperty().setSize(lotForm.getSize());
        lotToUpdate.getProperty().setDescription(lotForm.getDescription());
        lotToUpdate.setSellUntil(lotForm.getSellUntil());
        lotToUpdate.setMinPrice(lotForm.getMinPrice());
        return lotDAO.update(lotToUpdate);
    }
    public Lot update(int lotId, LotForm lotForm, byte[] byteFile) {
        Lot lotToUpdate = findById(lotId);
        lotToUpdate.getProperty().setWeight(lotForm.getWeight());
        lotToUpdate.getProperty().setSize(lotForm.getSize());
        lotToUpdate.getProperty().setDescription(lotForm.getDescription());
        lotToUpdate.getProperty().setPhoto(byteFile);
        lotToUpdate.setSellUntil(lotForm.getSellUntil());
        lotToUpdate.setMinPrice(lotForm.getMinPrice());
        return lotDAO.update(lotToUpdate);
    }


    public void delete(Lot lot) {
        lotDAO.delete(lot);
    }

    public void deleteByUserId(int userId) {

        lotDAO.deleteByUserId(userId);
    }
}
