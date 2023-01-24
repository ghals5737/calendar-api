package com.example.calendar.service.calendar;

import com.example.calendar.domain.calendar.Calendar;
import com.example.calendar.dto.calendar.request.CreateCalendarRequest;
import com.example.calendar.dto.calendar.request.UpdateCalendarRequest;
import com.example.calendar.dto.calendar.response.*;
import com.example.calendar.global.error.exception.CustomException;
import com.example.calendar.repository.calendar.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.calendar.global.error.ErrorCode.CALENDAR_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class CalendarService {
    private final CalendarRepository calendarRepository;

    @Transactional
    public SelectCalendarByIdResponse selectCalendarById(Long calendarId) throws Exception {
        return CalendarResponse.toSelectCalendarByIdResponse(calendarRepository.findById(calendarId).orElseThrow(Exception::new));
    }

    @Transactional
    public CreateCalendarResponse createCalendar(CreateCalendarRequest request) {
        return CalendarResponse.toCreateCalendarResponse(
                calendarRepository.save(request.toCalendar()));
    }

    @Transactional
    public DeleteCalendarResponse deleteCalendarById(Long calendarId) {
        Calendar calendar = calendarRepository.findById(calendarId).orElseThrow(() -> new CustomException(CALENDAR_NOT_FOUND));
        calendarRepository.delete(calendar);
        return CalendarResponse.toDeleteCalendarResponse(calendar);
    }

    @Transactional
    public UpdateCalendarResponse updateCalendar(UpdateCalendarRequest request) {
        Calendar calendar = calendarRepository.findById(request.getId()).orElseThrow(() -> new CustomException(CALENDAR_NOT_FOUND));
        calendar.updateCalendar(request);
        return CalendarResponse.toUpdateCalendarResponse(calendar);
    }
}
