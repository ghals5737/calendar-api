package com.example.calendar.repository.noti;

import com.example.calendar.dto.noti.response.SelectNotiByIdResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.calendar.domain.noti.QNoti.noti;

@RequiredArgsConstructor
@Repository
public class NotiQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public SelectNotiByIdResponse searchByNotiId(Long id) {
        return queryFactory.select(Projections.constructor(SelectNotiByIdResponse.class
                , noti.id
                , noti.notiType
                , noti.responseYn))
                .from(noti)
                .where(noti.id.eq(id),
                        noti.useYn.eq("Y"))
                .fetchOne();
    }
}
