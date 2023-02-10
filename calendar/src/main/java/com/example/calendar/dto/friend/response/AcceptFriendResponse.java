package com.example.calendar.dto.friend.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AcceptFriendResponse {
    private Long mainUserId;
    private Long subUserId;
}
