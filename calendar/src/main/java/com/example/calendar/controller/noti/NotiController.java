package com.example.calendar.controller.noti;

import com.example.calendar.dto.noti.response.SelectNotiByIdResponse;
import com.example.calendar.service.noti.NotiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notis")
public class NotiController {
    private final NotiService notiService;

    @GetMapping("{id}")
    public SelectNotiByIdResponse selectNotiById(@PathVariable Long id) {
        return notiService.selectNotiById(id);
    }

}


