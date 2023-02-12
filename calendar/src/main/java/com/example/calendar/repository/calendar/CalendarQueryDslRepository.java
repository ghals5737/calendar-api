package com.example.calendar.repository.calendar;

import com.example.calendar.dto.calendar.condition.CalendarSearchByUserIdCondition;
import com.example.calendar.dto.calendar.response.SelectCalendarByIdResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.calendar.domain.calendar.QCalendar.calendar;
import static com.example.calendar.domain.mapping.QUserCalendarMpng.userCalendarMpng;
import static com.example.calendar.domain.user.QUser.user;

@RequiredArgsConstructor
@Repository
public class CalendarQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<SelectCalendarByIdResponse> searchByUserId(CalendarSearchByUserIdCondition condition) {
        return queryFactory.select(Projections.constructor(SelectCalendarByIdResponse.class
                , calendar.id
                , calendar.title
                , calendar.description
                , calendar.category
                , calendar.color))
                .from(user)
                .innerJoin(userCalendarMpng)
                .on(user.id.eq(userCalendarMpng.userId))
                .innerJoin(calendar)
                .on(calendar.id.eq(userCalendarMpng.calendarId))
                .where(user.id.eq(condition.getUserId()))
                .fetch();
    }
}
