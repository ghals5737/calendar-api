package com.example.calendar.repository.friend;

import com.example.calendar.domain.user.QUser;
import com.example.calendar.dto.friend.response.SelectFriendListResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.calendar.domain.friend.QFriend.friend;
import static com.example.calendar.domain.user.QUser.user;

@RequiredArgsConstructor
@Repository
public class FriendQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;
    public List<SelectFriendListResponse> selectFriendList(Long userId) {
        return jpaQueryFactory.select(Projections.constructor(SelectFriendListResponse.class
                ,user.id
                ,user.email
                ,user.nickname))
                .from(friend)
                .innerJoin(user).on(user.id.eq(friend.id.receiveUserId))
                .where(friend.id.sendUserId.eq(userId))
                .fetch();
    }
}
