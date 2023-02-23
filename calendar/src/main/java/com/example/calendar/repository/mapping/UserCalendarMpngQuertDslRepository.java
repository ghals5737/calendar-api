package com.example.calendar.repository.mapping;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.calendar.domain.mapping.QUserCalendarMpng.userCalendarMpng;

@RequiredArgsConstructor
@Repository
public class UserCalendarMpngQuertDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<Integer> countUser(Long calendarId){
        return Optional.ofNullable(
                jpaQueryFactory.select(userCalendarMpng)
                .from(userCalendarMpng)
                .where(userCalendarMpng.calendarId.eq(calendarId))
                .fetch().size());
    }
}
