package com.example.calendar.dto.friend.response;

import com.example.calendar.domain.friend.Friend;

public class FriendResponse {
    public static AcceptFriendResponse toRequestFriendResponse(Friend save) {
        return AcceptFriendResponse.builder()
                .mainUserId(save.getId().getMainUserId())
                .subUserId(save.getId().getSubUserId())
                .build();
    }
}
