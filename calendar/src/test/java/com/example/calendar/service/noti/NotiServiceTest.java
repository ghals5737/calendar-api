package com.example.calendar.service.noti;

import com.example.calendar.domain.noti.Noti;
import com.example.calendar.domain.noti.NotiType;
import com.example.calendar.dto.noti.response.DeleteNotiByIdResponse;
import com.example.calendar.dto.noti.response.SelectNotiByIdResponse;
import com.example.calendar.repository.noti.NotiRepository;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@NoArgsConstructor
public class NotiServiceTest {

    @Autowired
    private NotiService notiService;
    @Autowired
    private NotiRepository notiRepository;

    Noti noti;

    @BeforeEach
    void createNoti() {
        noti = notiRepository.save(Noti.builder()
                .responseYn("Y")
                .notiType(NotiType.FRIEND_REQUEST)
                .useYn("Y")
                .build());
    }

    @AfterEach
    void clear() {
        notiRepository.deleteAll();
    }
    @Test
    void selectNotiByIdTest() {
        SelectNotiByIdResponse response = notiService.selectNotiById(noti.getId());
        assertThat(response.getResponseYn()).isEqualTo(noti.getResponseYn());
        assertThat(response.getNotiId()).isEqualTo(noti.getId());
//        assertThat(response.getNotiType()).isEqualTo(noti.getNotiType());
    }

    @Test
    void closeNotiByIdTest() {
        DeleteNotiByIdResponse expect = notiService.closeNotiById(noti.getId());
        assertThat(expect.getNotiId()).isEqualTo(noti.getId());
    }
}
