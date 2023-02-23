package com.example.calendar.repository.mapping;

import com.example.calendar.domain.mapping.UserCalendarMpng;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCalendarMpngRepository extends JpaRepository<UserCalendarMpng, Long> {
    List<UserCalendarMpng> findByUserId(Long userId);
    Optional<UserCalendarMpng> findByCalendarId(Long calendarId);

    Optional<UserCalendarMpng> findByCalendarIdAndUserId(Long calendarId,Long userId);

}
