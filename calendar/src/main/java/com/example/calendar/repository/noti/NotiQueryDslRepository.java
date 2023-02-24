package com.example.calendar.repository.noti;

import com.example.calendar.domain.noti.Noti;
import com.example.calendar.domain.noti.NotiType;
import com.example.calendar.dto.friend.request.AcceptFriendRequest;
import com.example.calendar.dto.friend.request.RequestFriendRequest;
import com.example.calendar.dto.noti.response.DeleteNotiByIdResponse;
import com.example.calendar.dto.noti.response.NotiResponse;
import com.example.calendar.dto.noti.response.SelectNotiByIdResponse;
import com.example.calendar.dto.noti.response.SelectNotiNotUsedByUserIdResponse;
import com.example.calendar.global.error.exception.CustomException;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.calendar.domain.noti.NotiType.FRIEND_ACCEPT;
import static com.example.calendar.domain.noti.QNoti.noti;
import static com.example.calendar.domain.user.QUser.user;
import static com.example.calendar.global.error.ErrorCode.DELETE_NOTI_FAILED;
import static com.example.calendar.global.error.ErrorCode.UPDATE_NOTI_TYPE_FAILED;

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

    public DeleteNotiByIdResponse updateUseYnNById(Long id) {
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
                , noti.notiType
                , ExpressionUtils.as(
                        JPAExpressions.select(user.id)
                                .from(user)
                                .where(user.id.eq(noti.sendUserId)), "sendUserId")
                , ExpressionUtils.as(
                        JPAExpressions.select(user.nickname)
                                .from(user)
                                .where(user.id.eq(noti.sendUserId)), "nickname")
                , ExpressionUtils.as(
                        JPAExpressions.select(user.email)
                                .from(user)
                                .where(user.id.eq(noti.sendUserId)), "email")
        ))
                .from(user)
                .innerJoin(noti)
                .on(user.id.eq(noti.receiveUserId))
                .where(user.id.eq(id),
                        noti.useYn.eq("Y"))
                .limit(5)
                .orderBy(noti.regDtm.desc())
                .fetch();
    }

    public boolean existsNoti(RequestFriendRequest request) {
        return queryFactory.select(noti.id).from(noti)
                .where(noti.receiveUserId.eq(request.getReceiveUserId()),
                        noti.sendUserId.eq(request.getSendUserId()),
                        noti.notiType.eq(NotiType.FRIEND_REQUEST),
                        noti.useYn.eq("Y"))
                .fetchFirst() != null;
    }

    public Noti findNotiBySendUserIdAndReceiveUserId(Long sendUserId, Long receiveUserId) {
        return queryFactory.selectFrom(noti)
                .where(noti.receiveUserId.eq(receiveUserId),
                        noti.sendUserId.eq(sendUserId),
                        noti.notiType.eq(FRIEND_ACCEPT),
                        noti.useYn.eq("Y"))
                .fetchOne();
    }

    public void updateNotiTypeAccept(AcceptFriendRequest request) {
        long execute = queryFactory.update(noti)
                .set(noti.useYn, "N")
                .where(noti.id.eq(request.getNotiId())
                        , noti.notiType.eq(NotiType.FRIEND_REQUEST)
                        , noti.useYn.eq("Y")
                )
                .execute();
        if (execute < 1) {
            throw new CustomException(UPDATE_NOTI_TYPE_FAILED);
        }
    }
}
