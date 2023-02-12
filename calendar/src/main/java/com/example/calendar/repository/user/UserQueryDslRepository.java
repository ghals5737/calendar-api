package com.example.calendar.repository.user;

import com.example.calendar.domain.user.QUser;
import com.example.calendar.domain.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public boolean existsByEmailAndSnsType(User user) {
        return queryFactory.from(QUser.user)
                .where(QUser.user.email.eq(user.getEmail())
//                        , QUser.user.snsType.eq(user.getSnsType()) // todo snstype 추가되면
                )
                .select(QUser.user.id).fetchFirst() != null;
    }
}
