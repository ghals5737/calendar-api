package com.example.calendar.dto.user.response;

import com.example.calendar.domain.user.type.SnsType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserResponse {
    private Long userId;
    private String email;
    private String nickname;
    private LocalDate birthday;
    private SnsType snsType;
}
