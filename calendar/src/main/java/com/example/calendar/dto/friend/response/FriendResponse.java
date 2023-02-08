package com.example.calendar.dto.friend.response;

import com.example.calendar.domain.friend.Friend;

public class FriendResponse {
    public static RequestFriendResponse toRequestFriendResponse(Friend save) {
        return RequestFriendResponse.builder()
                .mainUserId(save.getId().getMainUserId())
                .subUserId(save.getId().getSubUserId())
                .build();
    }
}
