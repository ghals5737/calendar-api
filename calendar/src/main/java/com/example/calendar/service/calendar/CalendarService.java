package com.example.calendar.service.calendar;

import com.example.calendar.domain.calendar.Calendar;
import com.example.calendar.dto.calendar.request.CreateCalendarRequest;
import com.example.calendar.dto.calendar.response.CalendarResponse;
import com.example.calendar.dto.calendar.response.CreateCalendarResponse;
import com.example.calendar.dto.calendar.response.DeleteCalendarResponse;
import com.example.calendar.dto.calendar.response.SelectCalendarByIdResponse;
import com.example.calendar.repository.calendar.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class CalendarService {
    private final CalendarRepository calendarRepository;

    public SelectCalendarByIdResponse selectCalendarById(Long calendarId) throws Exception {
        return CalendarResponse.toSelectCalendarByIdResponse(calendarRepository.findById(calendarId).orElseThrow(Exception::new));
    }

    public CreateCalendarResponse createCalendar(CreateCalendarRequest request) {
        return CalendarResponse.toCreateCalendarResponse(
                calendarRepository.save(request.toCalendar()));
    }

    public DeleteCalendarResponse deleteCalendarById(Long calendarId) {
        Calendar calendar = calendarRepository.findById(calendarId).orElseThrow(NoSuchElementException::new);
        calendarRepository.delete(calendar);
        return CalendarResponse.toDeleteCalendarResponse(calendar);
    }
}
