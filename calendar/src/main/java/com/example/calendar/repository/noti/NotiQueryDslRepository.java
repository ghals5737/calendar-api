package com.example.calendar.repository.noti;

import com.example.calendar.dto.noti.response.DeleteNotiByIdResponse;
import com.example.calendar.dto.noti.response.NotiResponse;
import com.example.calendar.dto.noti.response.SelectNotiByIdResponse;
import com.example.calendar.dto.noti.response.SelectNotiNotUsedByUserIdResponse;
import com.example.calendar.global.error.exception.CustomException;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.calendar.domain.noti.QNoti.noti;
import static com.example.calendar.domain.user.QUser.user;
import static com.example.calendar.global.error.ErrorCode.DELETE_NOTI_FAILED;

@RequiredArgsConstructor
@Repository
public class NotiQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public SelectNotiByIdResponse searchByNotiId(Long id) {
        return queryFactory.select(Projections.constructor(SelectNotiByIdResponse.class
                , noti.id
                , noti.notiType))
                .from(noti)
                .where(noti.id.eq(id),
                        noti.useYn.eq("Y"))
                .fetchOne();
    }

    public DeleteNotiByIdResponse updateUseYnById(Long id) {
        long execute = queryFactory.update(noti)
                .set(noti.useYn, "N")
                .where(noti.id.eq(id))
                .execute();
        if (execute < 1) {
            throw new CustomException(DELETE_NOTI_FAILED);
        }
        return NotiResponse.toDeleteNotiByIdResponse(id);
    }

    public List<SelectNotiNotUsedByUserIdResponse> searchNotiNotUsedByUserId(Long id) {
        return queryFactory.select(Projections.constructor(SelectNotiNotUsedByUserIdResponse.class
                , noti.id
                , noti.notiType))
                .from(user)
                .innerJoin(noti)
                .on(user.id.eq(noti.receiveUserId))
                .where(user.id.eq(id),
                        noti.useYn.eq("Y"))
                .limit(5)
                .orderBy(noti.regDtm.desc())
                .fetch();
    }
}
