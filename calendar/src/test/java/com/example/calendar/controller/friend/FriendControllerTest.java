package com.example.calendar.controller.friend;

import com.example.calendar.domain.friend.Friend;
import com.example.calendar.domain.friend.FriendId;
import com.example.calendar.domain.user.User;
import com.example.calendar.dto.friend.request.AcceptFriendRequest;
import com.example.calendar.dto.friend.request.RefuseFriendRequest;
import com.example.calendar.dto.friend.request.RequestFriendRequest;
import com.example.calendar.dto.friend.response.RequestFriendResponse;
import com.example.calendar.repository.friend.FriendRepository;
import com.example.calendar.repository.user.UserRepository;
import com.example.calendar.service.friend.FriendService;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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
    private FriendService friendService;
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
    }

    @AfterEach
    void clear() {
        friendRepository.deleteAll();
    }

    @Test
    @DisplayName("?????? ?????? API ???????????? ??????")
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
                                fieldWithPath("sendUserId").description("?????? ????????? ?????????"),
                                fieldWithPath("receiveUserId").description("?????? ????????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("?????? ??????"),
                                fieldWithPath("body.result").description("API ??????????????????"),
                                fieldWithPath("body.data").description("??????"),
                                fieldWithPath("body.data.sendUserId").description("?????? ????????? ?????????"),
                                fieldWithPath("body.data.receiveUserId").description("?????? ????????? ?????????"),
                                fieldWithPath("body.data.regDtm").description("?????????"),
                                fieldWithPath("body.data.notiId").description("?????? ?????????"),
                                fieldWithPath("body.error").description("??????"),
                                fieldWithPath("statusCode").description("http status ????????????"),
                                fieldWithPath("statusCodeValue").description("http status ??????????????????")
                        )));
    }

    @Test
    @DisplayName("?????? ?????? API ???????????? ??????")
    public void acceptFriendTest() throws Exception {
        //given
        // ?????? ??????
        RequestFriendRequest request = RequestFriendRequest.builder()
                .sendUserId(sendUser.getId()) // ?????? ?????? ?????????
                .receiveUserId(receiveUser.getId())
                .build();
        RequestFriendResponse requestFriendResponse = friendService.requestToBeFriends(request);
        Long notiId = requestFriendResponse.getNotiId();
        AcceptFriendRequest acceptRequest = AcceptFriendRequest.builder()
                .sendUserId(sendUser.getId())
                .receiveUserId(receiveUser.getId())
                .notiId(notiId)
                .build();

        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/api/friends/accept")
                        .content(objectMapper.writeValueAsString(acceptRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("friend-accept",
                       requestFields(
                                fieldWithPath("sendUserId").description("?????? ????????? ?????????"),
                                fieldWithPath("receiveUserId").description("?????? ????????? ?????????"),
                                fieldWithPath("notiId").description("?????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("?????? ??????"),
                                fieldWithPath("body.result").description("API ??????????????????"),
                                fieldWithPath("body.data").description("??????"),
                                fieldWithPath("body.data.sendUserId").description("?????? ????????? ?????????"),
                                fieldWithPath("body.data.receiveUserId").description("?????? ????????? ?????????"),
                                fieldWithPath("body.error").description("??????"),
                                fieldWithPath("statusCode").description("http status ????????????"),
                                fieldWithPath("statusCodeValue").description("http status ??????????????????")
                        )));
    }

    @Test
    @DisplayName("?????? ?????? API ???????????? ??????")
    public void refuseFriendTest() throws Exception {
        //given
        // ?????? ??????
        RequestFriendRequest request = RequestFriendRequest.builder()
                .sendUserId(sendUser.getId()) // ?????? ?????? ?????????
                .receiveUserId(receiveUser.getId())
                .build();
        RequestFriendResponse requestFriendResponse = friendService.requestToBeFriends(request);
        Long notiId = requestFriendResponse.getNotiId();
        RefuseFriendRequest refuseRequest = RefuseFriendRequest.builder()
                .sendUserId(requestFriendResponse.getReceiveUserId())
                .receiveUserId(requestFriendResponse.getSendUserId())
                .notiId(notiId)
                .build();

        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/api/friends/refuse")
                        .content(objectMapper.writeValueAsString(refuseRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("friend-refuse",

                        responseFields(
                                fieldWithPath("headers").description("?????? ??????"),
                                fieldWithPath("body.result").description("API ??????????????????"),
                                fieldWithPath("body.data").description("??????"),
                                fieldWithPath("body.data.notiId").description("?????? ?????????"),
                                fieldWithPath("body.data.sendUserId").description("?????? ????????? ?????????"),
                                fieldWithPath("body.data.receiveUserId").description("?????? ????????? ?????????"),
                                fieldWithPath("body.error").description("??????"),
                                fieldWithPath("statusCode").description("http status ????????????"),
                                fieldWithPath("statusCodeValue").description("http status ??????????????????")
                        )));
    }

    @Test
    @DisplayName("?????? ?????? API ???????????? ??????")
    public void selectFriendListTest() throws Exception {
        //given
        // ?????? ??????
        friendRepository.save(Friend.builder()
                .id(FriendId.builder()
                        .sendUserId(sendUser.getId())
                        .receiveUserId(receiveUser.getId())
                        .build())
                .build());

        //when,then
        this.mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .get("/api/friends/users/{userId}",sendUser.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("select-friends",
                        responseFields(
                                fieldWithPath("headers").description("?????? ??????"),
                                fieldWithPath("body.result").description("API ??????????????????"),
                                fieldWithPath("body.data").description("??????"),
                                fieldWithPath("body.data.[].userId").description("?????? ID"),
                                fieldWithPath("body.data.[].email").description("?????? ?????????"),
                                fieldWithPath("body.data.[].nickname").description("?????? ?????????"),
                                fieldWithPath("body.error").description("??????"),
                                fieldWithPath("statusCode").description("http status ????????????"),
                                fieldWithPath("statusCodeValue").description("http status ??????????????????")
                        )));
    }
}
