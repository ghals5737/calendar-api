package com.example.calendar.controller.user;

import com.example.calendar.domain.user.User;
import com.example.calendar.domain.user.type.SnsType;
import com.example.calendar.dto.user.request.CreateSnsUserRequest;
import com.example.calendar.dto.user.request.CreateUserRequest;
import com.example.calendar.dto.user.request.LoginSnsUserRequest;
import com.example.calendar.dto.user.request.UpdateUserRequest;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    public void create() {
        user = userRepository.save(User.builder()
                .nickname("staasdfr")
                .password("pw")
                .email("absdfadfc@gmail.com")
                .snsType(SnsType.MINICAL)
                .birthday(LocalDate.of(2023, 1, 26))
                .build());
    }

    @AfterEach
    public void clear() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("????????? ?????? API ???????????? ??????")
    public void createUserTest() throws Exception {
        //given
        CreateUserRequest request = CreateUserRequest.builder()
                .nickname("testnickname")
                .email("abc@gmail.com")
                .birthday(LocalDate.now())
                .password("abcdefg")
                .build();

        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/api/user")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-create",
                        requestFields(
                                fieldWithPath("nickname").description("????????? ?????????"),
                                fieldWithPath("birthday").description("????????? ????????????"),
                                fieldWithPath("email").description("????????? ?????????"),
                                fieldWithPath("password").description("????????? ????????????")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("?????? ??????"),
                                fieldWithPath("body.result").description("API ??????????????????"),
                                fieldWithPath("body.data").description("??????"),

                                fieldWithPath("body.data.userId").description("????????? id"),

                                fieldWithPath("body.data.userId").description("????????? ?????????"),

                                fieldWithPath("body.data.nickname").description("????????? ?????????"),
                                fieldWithPath("body.data.birthday").description("????????? ????????????"),
                                fieldWithPath("body.data.email").description("????????? ?????????"),
                                fieldWithPath("body.data.password").description("????????? ????????????"),
                                fieldWithPath("body.data.snsType").description("????????? ????????? ??????"),
                                fieldWithPath("body.error").description("??????"),
                                fieldWithPath("statusCode").description("http status ????????????"),
                                fieldWithPath("statusCodeValue").description("http status ??????????????????")
                        )));
    }

    @Test
    @DisplayName("SNS????????? ?????? API ???????????? ??????")
    public void createSnsUserTest() throws Exception {
        //given
        CreateSnsUserRequest request = CreateSnsUserRequest.builder()
                .email("sns@gmail.com")
                .birthday(null)
                .snsType(SnsType.GOOGLE)
                .build();

        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/api/user/sns")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-sns-create",
                        requestFields(
                                fieldWithPath("email").description("????????? ?????????"),
                                fieldWithPath("snsType").description("????????? ????????? ??????"),
                                fieldWithPath("birthday").description("????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("?????? ??????"),
                                fieldWithPath("body.result").description("API ??????????????????"),
                                fieldWithPath("body.data").description("??????"),
                                fieldWithPath("body.data.userId").description("????????? id"),
                                fieldWithPath("body.data.nickname").description("????????? ?????????"),
                                fieldWithPath("body.data.birthday").description("????????? ????????????"),
                                fieldWithPath("body.data.email").description("????????? ?????????"),
                                fieldWithPath("body.data.password").description("????????? ????????????"),
                                fieldWithPath("body.data.snsType").description("????????? ????????? ??????"),
                                fieldWithPath("body.error").description("??????"),
                                fieldWithPath("statusCode").description("http status ????????????"),
                                fieldWithPath("statusCodeValue").description("http status ??????????????????")
                        )));
    }

    @Test
    @DisplayName("????????? ???????????? ?????? ???????????? API ???????????? ??????")
    public void selectUserByIdTest() throws Exception {
        //given
        User expect = userRepository.save(User.builder()
                .nickname("test nickname")
                .email("abc@gmail.com")
                .birthday(LocalDate.now())
                .password("abcdefg")
                .build());

        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .get("/api/user/{id}", expect.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-selectByID"
                        , pathParameters(
                                parameterWithName("id").description("????????? ????????? ID")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("?????? ??????"),
                                fieldWithPath("body.result").description("API ??????????????????"),
                                fieldWithPath("body.error").description("??????"),
                                fieldWithPath("body.data.userId").description("????????? ID"),
                                fieldWithPath("body.data.nickname").description("????????? ?????????"),
                                fieldWithPath("body.data.birthday").description("????????? ????????????"),
                                fieldWithPath("body.data.email").description("????????? ?????????"),
                                fieldWithPath("body.data.password").description("????????? ????????????"),
                                fieldWithPath("body.data.snsType").description("????????? ????????? ??????"),
                                fieldWithPath("statusCode").description("http status ????????????"),
                                fieldWithPath("statusCodeValue").description("http status ??????????????????")
                        )));
    }

    @Test
    @DisplayName("????????? ???????????? ???????????? API ???????????? ??????")
    public void deleteUserByIdTest() throws Exception {
        //given
        User expect = userRepository.save(User.builder()
                .nickname("test nickname")
                .email("abc@gmail.com")
                .birthday(LocalDate.now())
                .password("abcdefg")
                .build());
        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .delete("/api/user/{id}", expect.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-selectByID"
                        , pathParameters(
                                parameterWithName("id").description("????????? ????????? id")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("?????? ??????"),
                                fieldWithPath("statusCode").description("http status ????????????"),
                                fieldWithPath("body.error").description("??????"),
                                fieldWithPath("body.result").description("API ??????????????????"),
                                fieldWithPath("body.data.userId").description("????????? ????????? ID"),
                                fieldWithPath("statusCodeValue").description("http status ??????????????????")
                        )));
    }

    @Test
    @DisplayName("????????? ?????? API ???????????? ??????")
    public void updateUserTest() throws Exception {
        //given
        UpdateUserRequest request = UpdateUserRequest.builder()
                .id(user.getId())
                .nickname("updatenickname")
                .email("updateemail@gmail.com")
                .birthday(LocalDate.of(2000, 1, 24))
                .password("updatepassword")
                .snsType(SnsType.GOOGLE)
                .build();

        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .put("/api/user")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-update",
                        requestFields(
                                fieldWithPath("id").description("????????? ?????????"),
                                fieldWithPath("nickname").description("????????? ?????????"),
                                fieldWithPath("birthday").description("????????? ????????????"),
                                fieldWithPath("email").description("????????? ?????????"),
                                fieldWithPath("password").description("????????? ????????????"),
                                fieldWithPath("snsType").description("SNS ??????")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("?????? ??????"),
                                fieldWithPath("body.error").description("API ?????? ??????"),
                                fieldWithPath("body.result").description("API ??????????????????"),
                                fieldWithPath("body.data.userId").description("????????? ID"),
                                fieldWithPath("body.data.nickname").description("????????? ?????????"),
                                fieldWithPath("body.data.birthday").description("????????? ????????????"),
                                fieldWithPath("body.data.email").description("????????? ?????????"),
                                fieldWithPath("body.data.password").description("????????? ????????????"),
                                fieldWithPath("body.data.snsType").description("????????? ????????? ??????"),
                                fieldWithPath("statusCode").description("http status ????????????"),
                                fieldWithPath("statusCodeValue").description("http status ??????????????????")
                        )));
    }

    @Test
    @DisplayName("SNS ????????? ??????????????? API ???????????? ??????")
    public void loginSnsTest() throws Exception {
        //given
        User snsUser = userRepository.save(User.builder()
                .nickname("sns@gmail.com")
                .password(null)
                .email("sns@gmail.com")
                .snsType(SnsType.GOOGLE)
                .birthday(null)
                .build());

        LoginSnsUserRequest request = LoginSnsUserRequest.builder()
                .email(snsUser.getEmail())
                .snsType(snsUser.getSnsType())
                .build();

        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/api/user/sns-login")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-sns-login",
                        requestFields(
                                fieldWithPath("email").description("????????? ?????????"),
                                fieldWithPath("snsType").description("????????? ????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("?????? ??????"),
                                fieldWithPath("body.result").description("API ??????????????????"),
                                fieldWithPath("body.error").description("??????"),
                                fieldWithPath("body.data.userId").description("????????? ID"),
                                fieldWithPath("body.data.nickname").description("????????? ?????????"),
                                fieldWithPath("body.data.birthday").description("????????? ????????????"),
                                fieldWithPath("body.data.email").description("????????? ?????????"),
                                fieldWithPath("body.data.snsType").description("????????? ????????? ??????"),
                                fieldWithPath("statusCode").description("http status ????????????"),
                                fieldWithPath("statusCodeValue").description("http status ??????????????????")
                        )));
    }

}
