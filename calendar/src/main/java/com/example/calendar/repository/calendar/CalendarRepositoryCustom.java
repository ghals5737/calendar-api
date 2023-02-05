package com.example.calendar.repository.calendar;

import com.example.calendar.domain.calendar.Calendar;
import com.example.calendar.dto.calendar.condition.CalendarSearchByUserIdCondition;
import com.querydsl.core.Tuple;

import java.util.List;

public interface CalendarRepositoryCustom {
    List<Calendar> searchByUserId(CalendarSearchByUserIdCondition condition);

}
