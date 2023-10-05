package com.arthuryasak.dao;

import com.arthuryasak.exceptions.DAOException;
import com.arthuryasak.models.Lot;
import com.arthuryasak.models.User;

import java.util.List;


public interface LotDAO {
    List<Lot> getAll() throws DAOException;
    List<Lot> getAllForUser(User currentUser) throws DAOException;
    List<Lot> getSold(User currentUser) throws DAOException;
    List<Lot> getBought(User currentUser) throws DAOException;
    Lot getById(Integer lotId) throws DAOException;
    List<Lot> getByUserId(int userId) throws DAOException;
    List<Lot> getWhereLastCustomer(int userId) throws DAOException;
    List<Lot> getByNameOrTypeForUser(String keyWord, User currentUser) throws DAOException;
    void add(Lot lot) throws DAOException;
    Lot update(Lot lot) throws DAOException;
    void delete(Lot lot) throws DAOException;
    void deleteByUserId(int userId);
}
