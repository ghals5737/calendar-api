package com.example.calendar.repository.user;

import com.example.calendar.domain.user.QUser;
import com.example.calendar.domain.user.User;
import com.example.calendar.domain.user.type.SnsType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public boolean existsByEmailAndSnsType(User user) {
        return queryFactory.from(QUser.user)
                .where(QUser.user.email.eq(user.getEmail())
                       , QUser.user.snsType.eq(user.getSnsType())
                )
                .select(QUser.user.id).fetchFirst() != null;
    }

    public Optional<User> selectByEmailAndSnsType(String email, SnsType snsType) {
        return  Optional.ofNullable(queryFactory
                .select(QUser.user)
                .from(QUser.user)
                .where(eqEmailAndSnsType(email,snsType))
                .fetchOne());
    }

    public BooleanBuilder eqEmailAndSnsType(String email, SnsType snsType){
        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and(QUser.user.email.eq(email));
        if (snsType != null) {
            whereClause.and(QUser.user.snsType.eq(snsType));
        } else {
            whereClause.and(QUser.user.snsType.isNull());
        }
        return whereClause;
    }
}
