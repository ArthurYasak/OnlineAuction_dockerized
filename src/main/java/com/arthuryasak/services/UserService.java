package com.arthuryasak.services;

import com.arthuryasak.dao.UserDAO;
import com.arthuryasak.dao.UserDAOImpl;
import com.arthuryasak.models.Lot;
import com.arthuryasak.models.Role;
import com.arthuryasak.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDAO userDAO;

    public User findById(int id) {
        return userDAO.getById(id);
    }

    public User findUserByUsername(String username) {
        return userDAO.getByUsername(username);
    }

    public void saveUser(User user) {
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setUserRoles(roles);
        userDAO.add(user);
    }

    public void deleteById(Integer id, List<Lot> buyingLots) {
        userDAO.deleteById(id);
    }

    public User updateUser(User user) {
        user = userDAO.update(user);
        return user;
    }

    public List<User> findAll(int userId) {
        return userDAO.getAll(userId);
    }

    public void deleteAll() {
        userDAO.deleteAll();
    }

}
