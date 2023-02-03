package com.example.calendar.repository.calendar;

import com.example.calendar.dto.calendar.condition.CalendarSearchByUserIdCondition;
import com.querydsl.core.Tuple;

import java.util.List;

public interface CalendarRepositoryCustom {
    List<Tuple> searchByUserId(CalendarSearchByUserIdCondition condition);

}
