package com.example.calendar.service.noti;

import com.example.calendar.dto.noti.response.DeleteNotiByIdResponse;
import com.example.calendar.dto.noti.response.SelectNotiByIdResponse;
import com.example.calendar.dto.noti.response.SelectNotiNotUsedByUserIdResponse;
import com.example.calendar.global.error.exception.CustomException;
import com.example.calendar.repository.noti.NotiQueryDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.example.calendar.global.error.ErrorCode.DELETE_NOTI_FAILED;
import static com.example.calendar.global.error.ErrorCode.NOTI_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class NotiService {

    private final NotiQueryDslRepository notiQueryDslRepository;

    @Transactional
    public SelectNotiByIdResponse selectNotiById(Long id) {
        return Optional.ofNullable(notiQueryDslRepository.searchByNotiId(id))
                .orElseThrow(() -> new CustomException(NOTI_NOT_FOUND));
    }

    @Transactional
    public DeleteNotiByIdResponse closeNotiById(Long id) {
        return Optional.ofNullable(notiQueryDslRepository.updateUseYnById(id))
                .orElseThrow(() -> new CustomException(DELETE_NOTI_FAILED));
    }

    public List<SelectNotiNotUsedByUserIdResponse> selectNotiNotUsedByUserId(Long id) {
        return Optional.ofNullable(notiQueryDslRepository.searchNotiNotUsedByUserId(id))
                .orElseThrow(() -> new CustomException(DELETE_NOTI_FAILED));
    }
}
