package com.example.calendar.dto.friend.response;

import lombok.*;

import java.time.LocalDateTime;
@ToString
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
