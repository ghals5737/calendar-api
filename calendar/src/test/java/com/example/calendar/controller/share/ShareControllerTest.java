package com.example.calendar.controller.share;

import com.example.calendar.domain.calendar.Calendar;
import com.example.calendar.domain.category.type.CategoryType;
import com.example.calendar.domain.user.User;
import com.example.calendar.domain.user.type.SnsType;
import com.example.calendar.dto.share.request.ShareCalendarRequest;
import com.example.calendar.repository.calendar.CalendarRepository;
import com.example.calendar.repository.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
@SpringBootTest
public class ShareControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    private User user;
    private Calendar calendar;
    private Calendar calendar2;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CalendarRepository calendarRepository;

    @BeforeEach
    public void create() {
        user = userRepository.save(User.builder()
                .nickname("star")
                .password("pw")
                .email("abc@gmail.com")
                .snsType(SnsType.MINICAL)
                .birthday(LocalDate.of(2023, 1, 26))
                .build());

        calendar = calendarRepository.save(
                Calendar.builder()
                        .color("yellow")
                        .title("shareTest1")
                        .category(CategoryType.TRIP)
                        .description("trip plan")
                        .build());

        calendar2 = calendarRepository.save(
                Calendar.builder()
                        .color("yellow")
                        .title("shareTest2")
                        .category(CategoryType.WORK)
                        .description("study plan")
                        .build());
    }

    @Rollback(false)
    @Test
    @DisplayName("????????? ?????? API ???????????? ??????")
    public void shareCalendarTest() throws Exception {
        List<Long> calendarIds = List.of(calendar.getId(), calendar2.getId());
        //given
        ShareCalendarRequest request = ShareCalendarRequest.builder()
                .calendarIds(calendarIds)
                .receiveUserId(user.getId())
                .build();

        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/api/share")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("share-calendar",
                        requestFields(
                                fieldWithPath("receiveUserId").description("?????? ????????? ?????????"),
                                fieldWithPath("calendarIds").description("?????? ??? ????????? ????????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("?????? ??????"),
                                fieldWithPath("body.result").description("API ??????????????????"),
                                fieldWithPath("body.data").description("??????"),
                                fieldWithPath("body.data.userId").description("?????? ?????? ????????? id"),
                                fieldWithPath("body.data.calendarIds").description("?????? ??? ????????? ????????? ?????????"),
                                fieldWithPath("body.error").description("??????"),
                                fieldWithPath("statusCode").description("http status ????????????"),
                                fieldWithPath("statusCodeValue").description("http status ??????????????????")
                        )));
    }
}
