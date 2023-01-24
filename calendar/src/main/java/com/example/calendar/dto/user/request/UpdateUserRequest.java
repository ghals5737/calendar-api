package com.example.calendar.dto.user.request;

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
public class UpdateUserRequest {
    @NotBlank
    private Long id;
    @NotBlank @Email
    private String email;
    @NotBlank
    private String nickname;
    @NotBlank @DateTimeFormat(pattern = "yyyyMMdd")
    @PastOrPresent
    private LocalDate birthday;
    @NotBlank
    private String password;
}
