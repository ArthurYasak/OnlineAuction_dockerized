package com.arthuryasak.services;

import com.arthuryasak.dao.UserDAOImpl;
import com.arthuryasak.models.Lot;
import com.arthuryasak.models.Role;
import com.arthuryasak.models.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserService {

    private UserDAOImpl usersDAO = new UserDAOImpl();

    public UserService() {

    }

    public User findById(int id) {
        return usersDAO.getById(id);
    }

    public User findUserByUsername(String username) {
        return usersDAO.getByUsername(username);
    }

    public void saveUser(User user) {
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setUserRoles(roles);
        usersDAO.add(user);
    }

    public void deleteById(Integer id, List<Lot> buyingLots) {
        usersDAO.deleteById(id);
    }

    public User updateUser(User user) {
        user = usersDAO.update(user);
        return user;
    }

    public List<User> findAll(int userId) {
        return usersDAO.getAll(userId);
    }

    public void deleteAll() {
        usersDAO.deleteAll();
    }

}
