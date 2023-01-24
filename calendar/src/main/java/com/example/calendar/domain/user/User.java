package com.example.calendar.domain.user;

import com.example.calendar.dto.user.request.UpdateUserRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nickname;
    private LocalDate birthday;
    private String password;

    public void updateUser(UpdateUserRequest request) {
        this.email = request.getEmail();
        this.nickname = request.getNickname();
        this.birthday = request.getBirthday();
        this.password = request.getPassword();
    }
}
