package com.example.calendar.service.share;

import com.example.calendar.domain.mapping.UserCalendarMpng;
import com.example.calendar.dto.share.request.ShareCalendarRequest;
import com.example.calendar.dto.share.response.ShareCalendarResponse;
import com.example.calendar.dto.share.response.ShareResponse;
import com.example.calendar.repository.mapping.UserCalendarMpngQueryDslRepository;
import com.example.calendar.repository.mapping.UserCalendarMpngRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ShareService {

    private final UserCalendarMpngRepository userCalendarMpngRepository;
    private final UserCalendarMpngQueryDslRepository userCalendarMpngQueryDslRepository;

    @Transactional
    public ShareCalendarResponse shareCalendar(ShareCalendarRequest request) {

        List<Long> existCalendarIds = userCalendarMpngQueryDslRepository.searchExistCalendarIds(request);

        List<Long> calendarIds = request.getCalendarIds();
        if (existCalendarIds.size() > 0) {
            calendarIds.removeAll(existCalendarIds);
        }

        calendarIds.stream().forEach(calendarId ->
                userCalendarMpngRepository.save(UserCalendarMpng.builder()
                        .calendarId(calendarId)
                        .userId(request.getReceiveUserId())
                        .build()
                ));

        return ShareResponse.toShareCalendarResponse(ShareCalendarResponse.builder()
                .userId(request.getReceiveUserId())
                .calendarIds(calendarIds)
                .build());
    }
}
