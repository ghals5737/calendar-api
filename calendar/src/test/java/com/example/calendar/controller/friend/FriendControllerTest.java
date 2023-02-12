package com.example.calendar.controller.friend;

import com.example.calendar.domain.user.User;
import com.example.calendar.dto.friend.request.AcceptFriendRequest;
import com.example.calendar.dto.friend.request.RequestFriendRequest;
import com.example.calendar.repository.friend.FriendRepository;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
public class FriendControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    private User sendUser;
    private User receiveUser;

    @BeforeEach
    void before() {
        sendUser = userRepository.save(User.builder()
                .nickname("mainUser")
                .password("pw")
                .email("mainUser@gmail.com")
                .birthday(LocalDate.of(2023, 1, 26))
                .build());
        receiveUser = userRepository.save(User.builder()
                .nickname("subUser")
                .password("pw")
                .email("subUser@gmail.com")
                .birthday(LocalDate.of(2023, 2, 26))
                .build());
        userRepository.save(sendUser);
        userRepository.save(receiveUser);
    }

    @AfterEach
    void clear() {
        friendRepository.deleteAll();
    }

    @Test
    @DisplayName("친구 요청 API 정상동작 확인")
    public void requestFriendTest() throws Exception {
        //given
        RequestFriendRequest request = RequestFriendRequest.builder()
                .sendUserId(sendUser.getId())
                .receiveUserId(receiveUser.getId())
                .build();

        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/api/friends/request")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("friend-create",
                        requestFields(
                                fieldWithPath("sendUserId").description("발신 사용자 아이디"),
                                fieldWithPath("receiveUserId").description("수신 사용자 아이디")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("해더 정보"),
                                fieldWithPath("body.result").description("API 실행결과정보"),
                                fieldWithPath("body.data").description("바디"),
                                fieldWithPath("body.data.sendUserId").description("메인 사용자 아이디"),
                                fieldWithPath("body.data.receiveUserId").description("서브 사용자 아이디"),
                                fieldWithPath("body.data.regDtm").description("등록일"),
                                fieldWithPath("body.data.notiId").description("알림 아이디"),
                                fieldWithPath("body.error").description("에러"),
                                fieldWithPath("statusCode").description("http status 상태코드"),
                                fieldWithPath("statusCodeValue").description("http status 상태숫자코드")
                        )));
    }

    @Test
    @DisplayName("친구 수락 API 정상동작 확인")
    public void acceptFriendTest() throws Exception {
        //given
        AcceptFriendRequest request = AcceptFriendRequest.builder()
                .sendUserId(sendUser.getId())
                .receiveUserId(receiveUser.getId())
                .build();

        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/api/friends/accept")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("calendar-create",
                        requestFields(
                                fieldWithPath("mainUserId").description("메인 사용자 아이디"),
                                fieldWithPath("subUserId").description("서브 사용자 아이디")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("해더 정보"),
                                fieldWithPath("body.result").description("API 실행결과정보"),
                                fieldWithPath("body.data").description("바디"),
                                fieldWithPath("body.data.mainUserId").description("메인 사용자 아이디"),
                                fieldWithPath("body.data.subUserId").description("서브 사용자 아이디"),
                                fieldWithPath("body.error").description("에러"),
                                fieldWithPath("statusCode").description("http status 상태코드"),
                                fieldWithPath("statusCodeValue").description("http status 상태숫자코드")
                        )));
    }
}
