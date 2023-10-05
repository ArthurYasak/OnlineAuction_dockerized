package com.arthuryasak.dao;

import com.arthuryasak.exceptions.DAOException;
import com.arthuryasak.models.User;

import java.util.List;

public interface UserDAO {
    List<User> getAll(int userId) throws DAOException;
    User getById(Integer userId) throws DAOException;
    User getByUsername(String username) throws DAOException;
    void add(User user) throws DAOException;
    User update(User user) throws DAOException;
    void deleteAll() throws DAOException;
    void deleteById(Integer userId) throws DAOException;
}
