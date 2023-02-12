package com.example.calendar.dto.friend.response;

import com.example.calendar.domain.friend.Friend;
import com.example.calendar.domain.noti.Noti;

public class FriendResponse {
    public static RequestFriendResponse toRequestFriendResponse(Noti save) {
        return RequestFriendResponse.builder()
                .sendUserId(save.getSendUserId())
                .receiveUserId(save.getReceiveUserId())
                .build();
    }
    public static AcceptFriendResponse toAcceptFriendResponse(Friend save) {
        return AcceptFriendResponse.builder()
                .sendUserId(save.getId().getSendUserId())
                .receiveUserId(save.getId().getReceiveUserId())
                .build();
    }
}
