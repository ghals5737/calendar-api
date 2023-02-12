package com.example.calendar.dto.friend.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefuseFriendResponse {
    private Long sendUserId;
    private Long receiveUserId;
    private Long notiId;
}
