package com.example.calendar.dto.user.request;

import com.example.calendar.domain.user.User;
import com.example.calendar.domain.user.type.SnsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank(message = "닉네임에 공백이 포함될 수 없습니다.")
    private String nickname;
    @NotBlank(message = "이메일이 공백일 수 없습니다.")
    @Email
    private String email;
    @NotNull(message = "생년월일이 공백일 수 없습니다.")
    @PastOrPresent
    private LocalDate birthday;
    @NotBlank(message = "비밀번호가 공백일 수 없습니다.")
    private String password;

    public User toUser() {
        return User.builder()
                .nickname(this.nickname)
                .birthday(this.birthday)
                .email(this.email)
                .password(this.password)
                .snsType(SnsType.MINICAL)
                .build();
    }
}
