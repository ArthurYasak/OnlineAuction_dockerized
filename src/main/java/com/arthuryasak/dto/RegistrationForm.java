package com.arthuryasak.dto;

import com.arthuryasak.models.AuthorizationData;
import com.arthuryasak.models.User;
import com.arthuryasak.models.UserData;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.*;

@Data
public class RegistrationForm {

    @NotNull(message = "Username shouldn't be empty")
    @Size(min = 2, max = 15,
            message = "Username length should be between 2 and 15 characters")
    private String username;

    @NotNull(message = "Password shouldn't be empty")
    private String password;

    @Size(min = 2, max = 15,
            message = "Name length should be between 2 and 15 characters")
    private String name;

    @Size(min=2, max=15, message = "Surname length should be between 2 and 15 characters")
    private String surname;

    @NotNull(message = "Age should not be empty")
    @Min(value = 0, message = "Age shouldn't be less than 0")
    private Integer age;

    private String telephone;

    @Email(message = "Email isn't correct")
    private String email;

    private String address;

    private byte[] photo;

    private static Logger logger = LoggerFactory.getLogger(RegistrationForm.class);

    public User toUser(PasswordEncoder passwordEncoder) {
        User user = new User();

        UserData userData = UserData.builder()
                .name(name)
                .surname(surname)
                .age(age)
                .telephone(telephone)
                .email(email)
                .address(address)
                .photo(photo)
                .build();

        user.setUserData(userData);

        AuthorizationData authorizationData = AuthorizationData.builder()
                .login(username)
                .password(passwordEncoder.encode(password))
                .build();

        user.setAuthorizationData(authorizationData);

        return user;
    }


}
