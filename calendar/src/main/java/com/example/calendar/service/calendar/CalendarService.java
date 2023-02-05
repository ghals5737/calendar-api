package com.example.calendar.service.calendar;

import com.example.calendar.domain.calendar.Calendar;
import com.example.calendar.domain.mapping.UserCalendarMpng;
import com.example.calendar.dto.calendar.condition.CalendarSearchByUserIdCondition;
import com.example.calendar.dto.calendar.request.CreateCalendarRequest;
import com.example.calendar.dto.calendar.request.UpdateCalendarRequest;
import com.example.calendar.dto.calendar.response.*;
import com.example.calendar.global.error.exception.CustomException;
import com.example.calendar.repository.calendar.CalendarRepository;
import com.example.calendar.repository.calendar.CalendarRepositoryCustom;
import com.example.calendar.repository.mapping.UserCalendarMpngRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.calendar.global.error.ErrorCode.CALENDAR_NOT_FOUND;
import static com.example.calendar.global.error.ErrorCode.USER_CALENDAR_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class CalendarService {
    private final CalendarRepository calendarRepository;
    private final CalendarRepositoryCustom calendarRepositoryCustom;
    private final UserCalendarMpngRepository userCalendarMpngRepository;

    @Transactional
    public SelectCalendarByIdResponse selectCalendarById(Long calendarId) throws Exception {
        return CalendarResponse.toSelectCalendarByIdResponse(calendarRepository.findById(calendarId).orElseThrow(Exception::new));
    }

    @Transactional
    public CreateCalendarResponse createCalendar(CreateCalendarRequest request) {
        // 캘린더 저장
        Calendar calendar = calendarRepository.save(request.toCalendar());
        // 유저캘린더 저장
        userCalendarMpngRepository.save(UserCalendarMpng.builder()
                .userId(request.getUserId())
                .calendarId(calendar.getId())
                .build());
        // 캘린더 리스판스로 변환 후 반환
        return CalendarResponse.toCreateCalendarResponse(calendar);
    }

    @Transactional
    public DeleteCalendarResponse deleteCalendarById(Long calendarId) {
        Calendar calendar = calendarRepository
                .findById(calendarId).orElseThrow(() -> new CustomException(CALENDAR_NOT_FOUND));
        calendarRepository.delete(calendar);

        UserCalendarMpng userCalendarMpng = userCalendarMpngRepository
                .findByCalendarId(calendarId).orElseThrow(() -> new CustomException(USER_CALENDAR_NOT_FOUND));
        userCalendarMpngRepository.delete(userCalendarMpng);

        return CalendarResponse.toDeleteCalendarResponse(calendar);
    }

    @Transactional
    public UpdateCalendarResponse updateCalendar(UpdateCalendarRequest request) {
        Calendar calendar = calendarRepository.findById(request.getId()).orElseThrow(() -> new CustomException(CALENDAR_NOT_FOUND));
        calendar.updateCalendar(request);
        return CalendarResponse.toUpdateCalendarResponse(calendar);
    }

    @Transactional // searchByUserId 로 리팩터링
    public List<SelectCalendarByIdResponse> selectCalendarByUserId(Long userId) {
        List<UserCalendarMpng> userCalendarMpngList = userCalendarMpngRepository.findByUserId(userId);
        List<Long> calendarIds = new ArrayList<>();
        for (UserCalendarMpng userCalendarMpng : userCalendarMpngList) {
            Long calendarId = userCalendarMpng.getCalendarId();
            calendarIds.add(calendarId);
        }
        List<Calendar> calendars = new ArrayList<>();
        for (Long calendarId : calendarIds) {
            Calendar calendar = calendarRepository.findById(calendarId)
                    .orElseThrow(() -> new CustomException(CALENDAR_NOT_FOUND));
            calendars.add(calendar);
        }
        List<SelectCalendarByIdResponse> result = new ArrayList<>();
        for (Calendar calendar : calendars) {
            SelectCalendarByIdResponse res = CalendarResponse.toSelectCalendarByIdResponse(calendar);
            result.add(res);
        }
        return result;
    }

    @Transactional
    public List<SelectCalendarByIdResponse> searchByUserId(Long userId) {
        CalendarSearchByUserIdCondition condition = CalendarSearchByUserIdCondition
                .builder()
                .userId(userId)
                .build();
        return Optional.ofNullable(calendarRepositoryCustom
                .searchByUserId(condition))
                .orElseThrow(() -> new CustomException(CALENDAR_NOT_FOUND))
                .stream()
                .map(CalendarResponse::toSelectCalendarByIdResponse)
                .collect(Collectors.toList())
                ;
    }
}
