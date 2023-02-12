package com.example.calendar.dto.friend.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcceptFriendRequest {
    private Long notiId;
    private Long sendUserId;
    private Long receiveUserId;
}
