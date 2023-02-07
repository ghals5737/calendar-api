package com.example.calendar.repository.noti;

import com.example.calendar.dto.noti.response.DeleteNotiByIdResponse;
import com.example.calendar.dto.noti.response.NotiResponse;
import com.example.calendar.dto.noti.response.SelectNotiByIdResponse;
import com.example.calendar.global.error.exception.CustomException;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.example.calendar.domain.noti.QNoti.noti;
import static com.example.calendar.global.error.ErrorCode.DELETE_NOTI_FAILED;

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

    public DeleteNotiByIdResponse setUseYnById(Long id) {
        long execute = queryFactory.update(noti)
                .set(noti.useYn, "N")
                .where(noti.id.eq(id),
                        noti.useYn.eq("Y"))
                .execute();
        if (execute < 1) {
            throw new CustomException(DELETE_NOTI_FAILED);
        }
        return NotiResponse.toDeleteNotiByIdResponse(id);
    }
}
