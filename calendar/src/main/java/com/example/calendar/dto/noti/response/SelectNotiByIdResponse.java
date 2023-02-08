package com.example.calendar.dto.noti.response;

import com.example.calendar.domain.noti.NotiType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SelectNotiByIdResponse {
    private Long notiId;
    private NotiType notiType;
    private String responseYn;
}
