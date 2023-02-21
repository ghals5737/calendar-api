package com.example.calendar.controller.share;

import com.example.calendar.dto.share.request.ShareCalendarRequest;
import com.example.calendar.dto.share.response.ShareCalendarResponse;
import com.example.calendar.service.share.ShareService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/share")
@CrossOrigin(origins = "*")
public class ShareController {
    private final ShareService shareService;

    @PostMapping
    public ShareCalendarResponse shareCalendar(@RequestBody @Valid ShareCalendarRequest request) throws Exception {
        return shareService.shareCalendar(request);
    }
}
