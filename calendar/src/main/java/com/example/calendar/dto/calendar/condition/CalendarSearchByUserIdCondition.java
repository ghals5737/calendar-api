package com.example.calendar.dto.calendar.condition;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class CalendarSearchByUserIdCondition {
    private Long userId;
}
