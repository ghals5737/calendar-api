package com.example.calendar.repository.schedule;

import com.example.calendar.domain.schedule.Schedule;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import static com.example.calendar.domain.schedule.QSchedule.schedule;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ScheduleQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<Schedule> findScheduleList(Long calendarId,String startYmd,String endYmd){
        return jpaQueryFactory.select(schedule)
                .from(schedule)
                .where(
                        schedule.calendarId.eq(calendarId),
                        schedule.startYmd.loe(endYmd),
                        schedule.endYmd.goe(startYmd)
                ).fetch();
    }
}
