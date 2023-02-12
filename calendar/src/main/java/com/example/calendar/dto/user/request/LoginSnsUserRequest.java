package com.example.calendar.dto.user.request;

import com.example.calendar.domain.user.type.SnsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginSnsUserRequest {
    @Email
    @NotEmpty
    private String email;
    @NotNull(message = "SnsType이 공백일 수 없습니다.")
    private SnsType snsType;
}
