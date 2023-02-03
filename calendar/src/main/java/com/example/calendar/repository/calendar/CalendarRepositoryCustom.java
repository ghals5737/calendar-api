package com.example.calendar.repository.calendar;

import com.example.calendar.dto.calendar.condition.CalendarSearchCondition;
import com.querydsl.core.Tuple;

import java.util.List;

public interface CalendarRepositoryCustom {
    List<Tuple> search(CalendarSearchCondition condition);

}
