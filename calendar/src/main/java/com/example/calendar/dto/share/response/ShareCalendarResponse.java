package com.example.calendar.dto.share.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShareCalendarResponse {
    private Long userId;
    private List<Long> calendarIds;
}
