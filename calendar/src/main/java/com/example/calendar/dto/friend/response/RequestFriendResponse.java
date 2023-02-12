package com.example.calendar.dto.friend.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestFriendResponse {
    private Long sendUserId;
    private Long receiveUserId;
    private LocalDateTime regDtm;
    private Long notiId;
}
