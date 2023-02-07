package com.example.calendar.dto.noti.response;

import com.example.calendar.domain.noti.Noti;

public class NotiResponse {
    public static SelectNotiByIdResponse toSelectNotiByIdResponse(Noti noti) {
        return SelectNotiByIdResponse.builder()
                .notiId(noti.getId())
                .responseYn(noti.getResponseYn())
                .notiType(noti.getNotiType())
                .build();

    }
}
