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
    @DisplayName("사용자 생성 API 정상동작 확인")
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
                                fieldWithPath("nickname").description("사용자 닉네임"),
                                fieldWithPath("birthday").description("사용자 생년월일"),
                                fieldWithPath("email").description("사용자 이메일"),
                                fieldWithPath("password").description("사용자 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("해더 정보"),
                                fieldWithPath("body.result").description("API 실행결과정보"),
                                fieldWithPath("body.data").description("바디"),

                                fieldWithPath("body.data.userId").description("사용자 id"),

                                fieldWithPath("body.data.userId").description("사용자 아이디"),

                                fieldWithPath("body.data.nickname").description("사용자 닉네임"),
                                fieldWithPath("body.data.birthday").description("사용자 생년월일"),
                                fieldWithPath("body.data.email").description("사용자 이메일"),
                                fieldWithPath("body.data.password").description("사용자 비밀번호"),
                                fieldWithPath("body.data.snsType").description("사용자 로그인 타입"),
                                fieldWithPath("body.error").description("에러"),
                                fieldWithPath("statusCode").description("http status 상태코드"),
                                fieldWithPath("statusCodeValue").description("http status 상태숫자코드")
                        )));
    }

    @Test
    @DisplayName("SNS사용자 생성 API 정상동작 확인")
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
                                fieldWithPath("email").description("사용자 이메일"),
                                fieldWithPath("snsType").description("사용자 로그인 타입"),
                                fieldWithPath("birthday").description("사용자 생일")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("해더 정보"),
                                fieldWithPath("body.result").description("API 실행결과정보"),
                                fieldWithPath("body.data").description("바디"),
                                fieldWithPath("body.data.userId").description("사용자 id"),
                                fieldWithPath("body.data.nickname").description("사용자 닉네임"),
                                fieldWithPath("body.data.birthday").description("사용자 생년월일"),
                                fieldWithPath("body.data.email").description("사용자 이메일"),
                                fieldWithPath("body.data.password").description("사용자 비밀번호"),
                                fieldWithPath("body.data.snsType").description("사용자 로그인 타입"),
                                fieldWithPath("body.error").description("에러"),
                                fieldWithPath("statusCode").description("http status 상태코드"),
                                fieldWithPath("statusCodeValue").description("http status 상태숫자코드")
                        )));
    }

    @Test
    @DisplayName("사용자 아이디로 단건 조회하는 API 정상동작 확인")
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
                                parameterWithName("id").description("조회할 사용자 ID")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("해더 정보"),
                                fieldWithPath("body.result").description("API 실행결과정보"),
                                fieldWithPath("body.error").description("에러"),
                                fieldWithPath("body.data.userId").description("사용자 ID"),
                                fieldWithPath("body.data.nickname").description("사용자 닉네임"),
                                fieldWithPath("body.data.birthday").description("사용자 생년월일"),
                                fieldWithPath("body.data.email").description("사용자 이메일"),
                                fieldWithPath("body.data.password").description("사용자 비밀번호"),
                                fieldWithPath("body.data.snsType").description("사용자 로그인 타입"),
                                fieldWithPath("statusCode").description("http status 상태코드"),
                                fieldWithPath("statusCodeValue").description("http status 상태숫자코드")
                        )));
    }

    @Test
    @DisplayName("사용자 아이디로 삭제하는 API 정상동작 확인")
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
                                parameterWithName("id").description("삭제할 사용자 id")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("해더 정보"),
                                fieldWithPath("statusCode").description("http status 상태코드"),
                                fieldWithPath("body.error").description("에러"),
                                fieldWithPath("body.result").description("API 실행결과정보"),
                                fieldWithPath("body.data.userId").description("삭제된 사용자 ID"),
                                fieldWithPath("statusCodeValue").description("http status 상태숫자코드")
                        )));
    }

    @Test
    @DisplayName("사용자 수정 API 정상동작 확인")
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
                                fieldWithPath("id").description("사용자 아이디"),
                                fieldWithPath("nickname").description("사용자 닉네임"),
                                fieldWithPath("birthday").description("사용자 생년월일"),
                                fieldWithPath("email").description("사용자 이메일"),
                                fieldWithPath("password").description("사용자 비밀번호"),
                                fieldWithPath("snsType").description("SNS 타입")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("해더 정보"),
                                fieldWithPath("body.error").description("API 에러 정보"),
                                fieldWithPath("body.result").description("API 실행결과정보"),
                                fieldWithPath("body.data.userId").description("사용자 ID"),
                                fieldWithPath("body.data.nickname").description("사용자 닉네임"),
                                fieldWithPath("body.data.birthday").description("사용자 생년월일"),
                                fieldWithPath("body.data.email").description("사용자 이메일"),
                                fieldWithPath("body.data.password").description("사용자 비밀번호"),
                                fieldWithPath("body.data.snsType").description("사용자 로그인 타입"),
                                fieldWithPath("statusCode").description("http status 상태코드"),
                                fieldWithPath("statusCodeValue").description("http status 상태숫자코드")
                        )));
    }

    @Test
    @DisplayName("SNS 사용자 로그인하는 API 정상동작 확인")
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
                                fieldWithPath("email").description("사용자 이메일"),
                                fieldWithPath("snsType").description("사용자 로그인 타입")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("해더 정보"),
                                fieldWithPath("body.result").description("API 실행결과정보"),
                                fieldWithPath("body.error").description("에러"),
                                fieldWithPath("body.data.userId").description("사용자 ID"),
                                fieldWithPath("body.data.nickname").description("사용자 닉네임"),
                                fieldWithPath("body.data.birthday").description("사용자 생년월일"),
                                fieldWithPath("body.data.email").description("사용자 이메일"),
                                fieldWithPath("body.data.snsType").description("사용자 로그인 타입"),
                                fieldWithPath("statusCode").description("http status 상태코드"),
                                fieldWithPath("statusCodeValue").description("http status 상태숫자코드")
                        )));
    }

}
