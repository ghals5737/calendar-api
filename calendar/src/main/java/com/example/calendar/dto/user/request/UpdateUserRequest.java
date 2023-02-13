package com.example.calendar.dto.user.request;

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
public class UpdateUserRequest {
    @NotNull(message = "아이디가 null일 수 없습니다.")
    private Long id;
    @NotBlank(message = "이메일이 공백일 수 없습니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;
    @NotBlank(message = "닉네임에 공백이 포함될 수 없습니다.")
    private String nickname;
    @NotNull(message = "생년월일 Null 일 수 없습니다.")
    @DateTimeFormat(pattern = "yyyyMMdd")
    @PastOrPresent(message = "생년월일이 현재 또는 과거여야합니다.")
    private LocalDate birthday;
    @NotBlank(message = "비밀번호가 공백일 수 없습니다.")
    private String password;
    @NotNull(message = "snstype이 공백일 수 없습니다.")
    private SnsType snsType;
}
