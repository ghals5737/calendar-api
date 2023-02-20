package com.example.calendar.dto.share.response;

import com.example.calendar.domain.mapping.UserCalendarMpng;

public class ShareResponse {
    public static ShareCalendarResponse toShareCalendarResponse(ShareCalendarResponse save) {
        return ShareCalendarResponse.builder()
                .userId(save.getUserId())
                .calendarIds(save.getCalendarIds())
                .build();
    }
}
