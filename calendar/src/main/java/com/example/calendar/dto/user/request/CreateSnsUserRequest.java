package com.example.calendar.dto.user.request;

import com.example.calendar.domain.user.User;
import com.example.calendar.domain.user.type.SnsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSnsUserRequest {
    @NotBlank(message = "이메일이 공백일 수 없습니다.")
    @Email
    private String email;
    private LocalDate birthday;
    @NotNull(message = "SnsType이 공백일 수 없습니다.")
    private SnsType snsType;

    public User toUser() {
        return User.builder()
                .nickname(this.email)
                .birthday(this.birthday)
                .email(this.email)
                .password(null)
                .snsType(this.snsType)
                .build();
    }
}