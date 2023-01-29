package com.example.calendar.repository.user;

import com.example.calendar.domain.user.UserCalendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCalendarRepository extends JpaRepository<UserCalendar, Long> {
    List<UserCalendar> findByUserId(Long userId);
    Optional<UserCalendar> findByCalendarId(Long calendarId);

}
