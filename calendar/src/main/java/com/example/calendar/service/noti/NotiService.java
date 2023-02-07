package com.example.calendar.service.noti;

import com.example.calendar.dto.noti.response.SelectNotiByIdResponse;
import com.example.calendar.global.error.ErrorCode;
import com.example.calendar.global.error.exception.CustomException;
import com.example.calendar.repository.noti.NotiQueryDslRepository;
import com.example.calendar.repository.noti.NotiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NotiService {
    private final NotiRepository notiRepository;
    private final NotiQueryDslRepository notiQueryDslRepository;

    public SelectNotiByIdResponse selectNotiById(Long id) {
        return Optional.ofNullable(notiQueryDslRepository.searchByNotiId(id))
                .orElseThrow(() -> new CustomException(ErrorCode.NOTI_NOT_FOUND));
    }
}
