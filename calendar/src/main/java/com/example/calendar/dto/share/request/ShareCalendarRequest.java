package com.example.calendar.dto.share.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShareCalendarRequest {
    private Long receiveUserId;
    private List<Long> calendarIds;

}
