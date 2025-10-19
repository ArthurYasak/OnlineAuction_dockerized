package com.arthuryasak.validators;

import com.arthuryasak.models.User;
import com.arthuryasak.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UsernameValidator implements Validator {
    UserService userService;

    @Autowired
    public UsernameValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean supports(Class<?> aClass) {
        return User.class.isAssignableFrom(aClass);
    }

    public void validate(Object obj, Errors errors) {
        User user = (User)obj;
        User userToCheck = userService.findUserByUsername(user.getAuthorizationData().getLogin());
        if (userToCheck != null) {
            errors.rejectValue("username", "label.validate.userExists");
        }
    }

}
