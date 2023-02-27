package com.example.calendar.dto.friend.response;

import lombok.*;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SelectFriendListResponse {
    private Long userId;
    private String email;
    private String nickname;
}
