package com.example.calendar.dto.user.request;

import com.example.calendar.domain.user.type.SnsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginSnsUserRequest {
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private SnsType snsType;
}
