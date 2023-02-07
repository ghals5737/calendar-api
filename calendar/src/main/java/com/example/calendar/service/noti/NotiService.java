package com.example.calendar.service.noti;

import com.example.calendar.dto.noti.response.DeleteNotiByIdResponse;
import com.example.calendar.dto.noti.response.SelectNotiByIdResponse;
import com.example.calendar.global.error.ErrorCode;
import com.example.calendar.global.error.exception.CustomException;
import com.example.calendar.repository.noti.NotiQueryDslRepository;
import com.example.calendar.repository.noti.NotiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.example.calendar.global.error.ErrorCode.DELETE_NOTI_FAILED;

@RequiredArgsConstructor
@Service
public class NotiService {
    private final NotiRepository notiRepository;
    private final NotiQueryDslRepository notiQueryDslRepository;

    @Transactional
    public SelectNotiByIdResponse selectNotiById(Long id) {
        return Optional.ofNullable(notiQueryDslRepository.searchByNotiId(id))
                .orElseThrow(() -> new CustomException(ErrorCode.NOTI_NOT_FOUND));
    }

    @Transactional
    public DeleteNotiByIdResponse closeNotiById(Long id) {
        return Optional.ofNullable(notiQueryDslRepository.setUseYnById(id))
                .orElseThrow(() -> new CustomException(DELETE_NOTI_FAILED));
    }
}
