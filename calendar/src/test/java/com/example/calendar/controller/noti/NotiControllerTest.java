package com.example.calendar.controller.noti;

import com.example.calendar.domain.calendar.Calendar;
import com.example.calendar.domain.noti.Noti;
import com.example.calendar.domain.noti.NotiType;
import com.example.calendar.domain.user.User;
import com.example.calendar.dto.calendar.request.CreateCalendarRequest;
import com.example.calendar.repository.calendar.CalendarRepository;
import com.example.calendar.repository.noti.NotiRepository;
import com.example.calendar.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
public class NotiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NotiRepository notiRepository;

    @AfterEach
    public void clear() {
        notiRepository.deleteAll();
    }
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
                .email("mainUser@gmail.com")
                .birthday(LocalDate.of(2023, 1, 26))
                .build());
        receiveUser = userRepository.save(User.builder()
                .nickname("receiveUser")
                .password("pw")
                .email("subUser@gmail.com")
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
    @Test
    @DisplayName("사용자 아이디로 최신 5건의 미열람 알림 조회 정상 동작 확인")
    public void selectNotiNotUsedByUserId() throws Exception {
        //given

        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .get("/api/notis/users/{id}", receiveUser.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("notis-selectByUserID"
                        , pathParameters(
                                parameterWithName("id").description("조회할 수신자 ID")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("해더 정보"),
                                fieldWithPath("body.result").description("API 실행결과정보"),
                                fieldWithPath("body.error").description("에러"),
                                fieldWithPath("body.data.[].notiId").description("알림 ID"),
                                fieldWithPath("body.data.[].notiType").description("알림 타입"),
                                fieldWithPath("statusCode").description("http status 상태코드"),
                                fieldWithPath("statusCodeValue").description("http status 상태숫자코드")
                        )));
    }
    @Test
    @DisplayName("알림 조회 API 정상동작 확인")
    public void selectNotiByIdTest() throws Exception {
        //given

        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .get("/api/notis/{id}", noti.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("notis-selectByID"
                        , pathParameters(
                                parameterWithName("id").description("조회할 알림 ID")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("해더 정보"),
                                fieldWithPath("body.result").description("API 실행결과정보"),
                                fieldWithPath("body.error").description("에러"),
                                fieldWithPath("body.data.notiId").description("알림 ID"),
                                fieldWithPath("body.data.notiType").description("알림 타입"),
                                fieldWithPath("statusCode").description("http status 상태코드"),
                                fieldWithPath("statusCodeValue").description("http status 상태숫자코드")
                        )));
    }
    @Test
    @DisplayName("알림 아이디로 use_yn N 처리 하는 API 정상동작 확인")
    public void deleteNotiByIdTest() throws Exception {
        //given
        Noti expect = notiRepository.save(Noti.builder()
                .notiType(NotiType.FRIEND_REQUEST)
                .useYn("Y")
                .build());

        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .delete("/api/notis/{id}", expect.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("notis-selectByID"
                        , pathParameters(
                                parameterWithName("id").description("삭제할 알림 id")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("해더 정보"),
                                fieldWithPath("statusCode").description("http status 상태코드"),
                                fieldWithPath("body.error").description("에러"),
                                fieldWithPath("body.result").description("API 실행결과정보"),
                                fieldWithPath("body.data.notiId").description("삭제된 알림 ID"),
                                fieldWithPath("statusCodeValue").description("http status 상태숫자코드")
                        )));
    }

}
