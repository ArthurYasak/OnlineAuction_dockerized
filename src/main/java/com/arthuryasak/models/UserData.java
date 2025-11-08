package com.arthuryasak.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_data")
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_data_id")
    @Setter(AccessLevel.PRIVATE)
    private Integer userDataId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)  // CascadeType.ALL
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

    private String surname;

    private Integer age;

    private String telephone;

    private String email;

    private String address;

    private byte[] photo;

    @Override
    public String toString() {
        return "UserData{" +
                "userDataId=" + userDataId +
                ", userId=" + user.getUserId() +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", photo=" + Arrays.toString(photo) +
                '}';
    }
}
