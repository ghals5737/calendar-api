package com.example.calendar.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SelectUserByEmailResponse {
    private Long userId;
    private String email;
    private String nickname;
}
