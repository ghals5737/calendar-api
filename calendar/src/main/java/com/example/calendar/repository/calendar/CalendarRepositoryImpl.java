package com.example.calendar.repository.calendar;

import com.example.calendar.dto.calendar.condition.CalendarSearchByUserIdCondition;
import com.example.calendar.dto.calendar.response.SelectCalendarByIdResponse;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import java.util.Calendar;
import java.util.List;

//import static com.example.calendar.domain.calendar.QCalendar.calendar;
//import static com.example.calendar.domain.user.QUser.user;
//import static com.example.calendar.domain.mapping.QUserCalendarMpng.userCalendarMpng;

public class CalendarRepositoryImpl implements CalendarRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public CalendarRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

//    @Override
//    public List<SelectCalendarByIdResponse> searchByUserId(CalendarSearchByUserIdCondition condition) {
//        return queryFactory.select(Projections.constructor(SelectCalendarByIdResponse.class
//                , calendar.id
//                , calendar.title
//                , calendar.description
//                , calendar.category
//                , calendar.color))
//                .from(calendar)
//                .innerJoin(userCalendarMpng)
//                .on(calendar.id.eq(userCalendarMpng.calendarId))
//                .innerJoin( user)
//                .on(user.id.eq(userCalendarMpng.userId))
//                .where(user.id.eq(condition.getUserId()))
//                .fetch();
//    }


}
