package com.example.calendar.dto.friend.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefuseFriendRequest {
    private Long notiID;
    private Long sendId;
    private Long receiveId;
}
