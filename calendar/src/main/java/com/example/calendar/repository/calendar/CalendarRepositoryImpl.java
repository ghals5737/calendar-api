package com.example.calendar.repository.calendar;

import com.example.calendar.domain.calendar.Calendar;
import com.example.calendar.domain.mapping.QUserCalendarMpng;
import com.example.calendar.dto.calendar.condition.CalendarSearchByUserIdCondition;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.calendar.domain.calendar.QCalendar.calendar;
import static com.example.calendar.domain.mapping.QUserCalendarMpng.userCalendarMpng;
import static com.example.calendar.domain.user.QUser.user;


@RequiredArgsConstructor
@Repository
public class CalendarRepositoryImpl implements CalendarRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Calendar> searchByUserId(CalendarSearchByUserIdCondition condition) {
        return queryFactory.select(calendar)
                .from(calendar)
                .leftJoin(userCalendarMpng)
                .on(calendar.id.eq(userCalendarMpng.calendarId))
                .leftJoin(user)
                .on(user.id.eq(userCalendarMpng.userId))
                .where(user.id.eq(condition.getUserId()))
                .fetch();
    }


}
