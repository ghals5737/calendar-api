package com.example.calendar.repository.mapping;

import com.example.calendar.domain.mapping.UserCalendarMpng;
import com.example.calendar.dto.share.request.ShareCalendarRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.calendar.domain.calendar.QCalendar.calendar;
import static com.example.calendar.domain.mapping.QUserCalendarMpng.userCalendarMpng;
import static com.example.calendar.domain.user.QUser.user;

@RequiredArgsConstructor
@Repository
public class UserCalendarMpngQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<Integer> countUser(Long calendarId) {
        return Optional.ofNullable(
                jpaQueryFactory.select(userCalendarMpng)
                        .from(userCalendarMpng)
                        .where(userCalendarMpng.calendarId.eq(calendarId))
                        .fetch().size());
    }

    public List<Long> searchExistCalendarIds(ShareCalendarRequest request) {
        return jpaQueryFactory.select(calendar.id)
                .from(user)
                .join(userCalendarMpng)
                .on(userCalendarMpng.userId.eq(user.id))
                .join(calendar)
                .on(calendar.id.eq(userCalendarMpng.calendarId))
                .where(user.id.eq(request.getReceiveUserId())
                        , calendar.id.in(request.getCalendarIds()))
                .fetch();


    }

    public List<UserCalendarMpng> findAllByUserId(Long receiveUserId) {
        return jpaQueryFactory.selectFrom(userCalendarMpng)
                .where(userCalendarMpng.userId.eq(receiveUserId)).fetch();

    }
}
