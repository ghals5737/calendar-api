package com.example.calendar.dto.user.request;

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
public class SelectUserByEmailRequest {
    @Email
    @NotEmpty
    private String email;
}
