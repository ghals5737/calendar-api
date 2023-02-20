package com.example.calendar.service.share;

import com.example.calendar.domain.mapping.UserCalendarMpng;
import com.example.calendar.dto.share.request.ShareCalendarRequest;
import com.example.calendar.dto.share.response.ShareCalendarResponse;
import com.example.calendar.dto.share.response.ShareResponse;
import com.example.calendar.repository.mapping.UserCalendarMpngRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShareService {

    private final UserCalendarMpngRepository userCalendarMpngRepository;

    public ShareCalendarResponse shareCalendar(ShareCalendarRequest request) {
        List<Long> calendarIds = request.getCalendarIds();

        List<UserCalendarMpng> results =
                calendarIds.stream().map(calendarId ->
                        userCalendarMpngRepository.save(
                                UserCalendarMpng.builder()
                                        .calendarId(calendarId)
                                        .userId(request.getReceiveUserId())
                                        .build())).collect(Collectors.toList());

        List<Long> calendarIdsResult = results.stream()
                .map(UserCalendarMpng::getCalendarId)
                .collect(Collectors.toList());

        return ShareResponse.toShareCalendarResponse(ShareCalendarResponse.builder()
                .userId(results.get(0).getUserId())
                .calendarIds(calendarIdsResult)
                .build());
    }
}
