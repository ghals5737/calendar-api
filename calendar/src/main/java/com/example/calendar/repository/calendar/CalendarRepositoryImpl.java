package com.example.calendar.repository.calendar;

import com.example.calendar.dto.calendar.condition.CalendarSearchCondition;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.calendar.domain.calendar.QCalendar.calendar;
import static com.example.calendar.domain.user.QUser.user;
import static com.example.calendar.domain.user.QUserCalendar.userCalendar;

public class CalendarRepositoryImpl implements CalendarRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public CalendarRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Tuple> search(CalendarSearchCondition condition) {
        return queryFactory.select(calendar.category,calendar.color,calendar.description,calendar.title)
                .from(calendar)
                .leftJoin(userCalendar)
                .on(calendar.id.eq(userCalendar.calendarId))
                .leftJoin( user)
                .on(user.id.eq(userCalendar.userId))
                .where(user.id.eq(condition.getUserId()))
                .fetch();
    }


}
