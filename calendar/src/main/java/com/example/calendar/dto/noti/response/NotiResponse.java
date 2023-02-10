package com.example.calendar.dto.noti.response;

import com.example.calendar.domain.noti.Noti;

public class NotiResponse {
    public static SelectNotiByIdResponse toSelectNotiByIdResponse(Noti noti) {
        return SelectNotiByIdResponse.builder()
                .notiId(noti.getId())
                .notiType(noti.getNotiType())
                .build();

    }

    public static DeleteNotiByIdResponse toDeleteNotiByIdResponse(Long id) {
        return DeleteNotiByIdResponse.builder()
                .notiId(id)
                .build();
    }
}
