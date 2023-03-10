package com.example.calendar.dto.friend.response;

import com.example.calendar.domain.friend.Friend;
import com.example.calendar.domain.noti.Noti;

public class FriendResponse {
    public static RequestFriendResponse toRequestFriendResponse(Noti save) {
        return RequestFriendResponse.builder()
                .sendUserId(save.getSendUserId())
                .receiveUserId(save.getReceiveUserId())
                .regDtm(save.getRegDtm())
                .notiId(save.getId())
                .build();
    }

    public static AcceptFriendResponse toAcceptFriendResponse(Friend save) {
        return AcceptFriendResponse.builder()
                .sendUserId(save.getId().getSendUserId())
                .receiveUserId(save.getId().getReceiveUserId())
                .build();
    }

    public static RefuseFriendResponse toRefuseFriendResponse(Noti noti) {
        return RefuseFriendResponse.builder()
                .notiId(noti.getId())
                .sendUserId(noti.getSendUserId())
                .receiveUserId(noti.getReceiveUserId())
                .build();
    }
}
