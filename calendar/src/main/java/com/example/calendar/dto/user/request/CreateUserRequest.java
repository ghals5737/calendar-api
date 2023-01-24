package com.example.calendar.dto.user.request;

import com.example.calendar.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank
    private String nickname;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @DateTimeFormat(pattern = "yyyyMMdd")
    @PastOrPresent
    private LocalDate birthday;
    @NotBlank
    private String password;

    public User toUser() {
        return User.builder()
                .nickname(this.nickname)
                .birthday(this.birthday)
                .email(this.email)
                .password(this.password)
                .build();
    }
}
