package com.example.calendar.controller.noti;

import com.example.calendar.dto.noti.response.DeleteNotiByIdResponse;
import com.example.calendar.dto.noti.response.SelectNotiByIdResponse;
import com.example.calendar.dto.noti.response.SelectNotiNotUsedByUserIdResponse;
import com.example.calendar.service.noti.NotiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notis")
@CrossOrigin(origins = "*")
public class NotiController {
    private final NotiService notiService;

    @GetMapping("{id}")
    public SelectNotiByIdResponse selectNotiById(@PathVariable Long id) {
        return notiService.selectNotiById(id);
    }

    @DeleteMapping("{id}")
    public DeleteNotiByIdResponse deleteNotiById(@PathVariable Long id) {
        return notiService.closeNotiById(id);
    }

    @GetMapping("/users/{id}")
    public List<SelectNotiNotUsedByUserIdResponse> selectNotiNotUsedByUserId(@PathVariable Long id) {
        return notiService.selectNotiNotUsedByUserId(id);
    }

}


