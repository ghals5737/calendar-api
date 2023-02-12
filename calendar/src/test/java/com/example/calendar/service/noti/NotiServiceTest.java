package com.example.calendar.service.noti;

import com.example.calendar.domain.noti.Noti;
import com.example.calendar.domain.noti.NotiType;
import com.example.calendar.domain.user.User;
import com.example.calendar.dto.noti.response.DeleteNotiByIdResponse;
import com.example.calendar.dto.noti.response.SelectNotiByIdResponse;
import com.example.calendar.dto.noti.response.SelectNotiNotUsedByUserIdResponse;
import com.example.calendar.global.error.exception.CustomException;
import com.example.calendar.repository.noti.NotiRepository;
import com.example.calendar.repository.user.UserRepository;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.calendar.global.error.ErrorCode.NOTI_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@NoArgsConstructor
public class NotiServiceTest {

    @Autowired
    private NotiService notiService;

    @Autowired
    private NotiRepository notiRepository;

    @Autowired
    private UserRepository userRepository;

    private Noti noti;
    private User sendUser;
    private User receiveUser;

    @BeforeEach
    void createNoti() {

        sendUser = userRepository.save(User.builder()
                .nickname("sendUser")
                .password("pw")
                .email("sendUser@gmail.com")
                .birthday(LocalDate.of(2023, 1, 26))
                .build());
        receiveUser = userRepository.save(User.builder()
                .nickname("receiveUser")
                .password("pw")
                .email("receiveUser@gmail.com")
                .birthday(LocalDate.of(2023, 2, 26))
                .build());
        userRepository.save(sendUser);
        userRepository.save(receiveUser);
        noti = notiRepository.save(Noti.builder()
                .notiType(NotiType.FRIEND_REQUEST)
                .useYn("Y")
                .sendUserId(sendUser.getId())
                .receiveUserId(receiveUser.getId())
                .regDtm(LocalDateTime.now())
                .build());
    }

    @AfterEach
    void clear() {
        notiRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 아이디로 열람하지 않았던 5건의 최신 알림 조회 정상 동작 확인")
    void selectNotiNotUsedByUserIdTest() {
        List<SelectNotiNotUsedByUserIdResponse> response = notiService.selectNotiNotUsedByUserId(receiveUser.getId());
        assertThat(response.get(0).getNotiId()).isEqualTo(noti.getId());
        assertThat(response.get(0).getNotiType()).isEqualTo(noti.getNotiType());
    }

    @Test
    @DisplayName("알림 아이디로 알림 단건 조회 정상 동작 확인")
    void selectNotiByIdTest() {
        SelectNotiByIdResponse response = notiService.selectNotiById(noti.getId());
        assertThat(response.getNotiId()).isEqualTo(noti.getId());
        assertThat(response.getNotiType()).isEqualTo(noti.getNotiType());
    }

    @Test
    @DisplayName("알림 아이디로 알림 닫기 처리 정상 동작 확인")
    void closeNotiByIdTest() {
        DeleteNotiByIdResponse expect = notiService.closeNotiById(noti.getId());
        assertThat(expect.getNotiId()).isEqualTo(noti.getId());
        Noti noti = notiRepository.findById(expect.getNotiId())
                .orElseThrow(() -> new CustomException(NOTI_NOT_FOUND));
        assertThat(noti.getId()).isEqualTo(expect.getNotiId());
    }
}
