package com.example.calendar.controller.calendar;

import com.example.calendar.domain.calendar.Calendar;
import com.example.calendar.domain.category.type.CategoryType;
import com.example.calendar.domain.mapping.UserCalendarMpng;
import com.example.calendar.domain.user.User;
import com.example.calendar.dto.calendar.request.CreateCalendarRequest;
import com.example.calendar.dto.calendar.request.UpdateCalendarRequest;
import com.example.calendar.repository.calendar.CalendarRepository;
import com.example.calendar.repository.mapping.UserCalendarMpngRepository;
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
class CalendarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCalendarMpngRepository userCalendarMpngRepository;

    @AfterEach
    public void clear() {
        calendarRepository.deleteAll();
        userCalendarMpngRepository.deleteAll();
        userRepository.deleteAll();
    }

    Calendar calendar;
    User user;
    UserCalendarMpng userCalendarMpng;

    @BeforeEach
    public void create() {

        calendar = calendarRepository.save(
                Calendar.builder()
                        .color("yellow")
                        .title("trip calendar")
                        .category(CategoryType.TRIP)
                        .description("for planning trips")
                        .build());

        user = userRepository.save(User.builder()
                .nickname("star")
                .password("pw")
                .email("abc@gmail.com")
                .birthday(LocalDate.of(2023, 1, 26))
                .build());

        userCalendarMpng = userCalendarMpngRepository.save(UserCalendarMpng.builder()
                .calendarId(calendar.getId())
                .userId(user.getId())
                .build());
    }

    @Test
    @DisplayName("????????? ?????? API ???????????? ??????")
    public void createCalendarTest() throws Exception {
        //given
        CreateCalendarRequest request = CreateCalendarRequest.builder()
                .title("test title")
                .color("test color")
                .category(CategoryType.COUPLE)
                .description("test desc")
                .userId(user.getId())
                .build();

        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .post("/api/calendar")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("calendar-create",
                        requestFields(
                                fieldWithPath("title").description("????????? ??????"),
                                fieldWithPath("color").description("????????? ??????"),
                                fieldWithPath("category").description("????????? ????????????"),
                                fieldWithPath("description").description("????????? ??????"),
                                fieldWithPath("userId").description("????????? ?????????")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("?????? ??????"),
                                fieldWithPath("body.result").description("API ??????????????????"),
                                fieldWithPath("body.data").description("??????"),
                                fieldWithPath("body.data.calendarId").description("????????? ?????????"),
                                fieldWithPath("body.data.title").description("????????? ??????"),
                                fieldWithPath("body.data.color").description("????????? ??????"),
                                fieldWithPath("body.data.category").description("????????? ????????????"),
                                fieldWithPath("body.data.description").description("????????? ??????"),
                                fieldWithPath("body.error").description("??????"),
                                fieldWithPath("statusCode").description("http status ????????????"),
                                fieldWithPath("statusCodeValue").description("http status ??????????????????")
                        )));
    }

    @Test
    @DisplayName("????????? ???????????? ?????? ???????????? API ???????????? ??????")
    public void selectCalendarByIdTest() throws Exception {
        //given


        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .get("/api/calendar/{id}", calendar.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("calendar-selectByID"
                        , pathParameters(
                                parameterWithName("id").description("????????? ????????? ID")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("?????? ??????"),
                                fieldWithPath("body.result").description("API ??????????????????"),
                                fieldWithPath("body.error").description("??????"),
                                fieldWithPath("body.data.calendarId").description("????????? ID"),
                                fieldWithPath("body.data.title").description("????????? ??????"),
                                fieldWithPath("body.data.color").description("????????? ??????"),
                                fieldWithPath("body.data.category").description("????????? ????????????"),
                                fieldWithPath("body.data.description").description("????????? ??????"),
                                fieldWithPath("statusCode").description("http status ????????????"),
                                fieldWithPath("statusCodeValue").description("http status ??????????????????")
                        )));
    }

    @Test
    @DisplayName("????????? ???????????? ?????? ???????????? ???????????? API ???????????? ??????")
    public void deleteCalendarByIdTest() throws Exception {
        //given

        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .delete("/api/calendar/{id}?userId="+user.getId(), calendar.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("calendar-delete"
                        , pathParameters(
                                parameterWithName("id").description("????????? ????????? id")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("?????? ??????"),
                                fieldWithPath("statusCode").description("http status ????????????"),
                                fieldWithPath("body.error").description("??????"),
                                fieldWithPath("body.result").description("API ??????????????????"),
                                fieldWithPath("body.data").description("????????? ????????? ID"),
                                fieldWithPath("statusCodeValue").description("http status ??????????????????")
                        )));
    }

    @Test
    @DisplayName("????????? ?????? API ???????????? ??????")
    public void updateCalendarTest() throws Exception {
        //given

        UpdateCalendarRequest request = UpdateCalendarRequest.builder()
                .id(calendar.getId())
                .title("update test")
                .category(CategoryType.WORK)
                .description("update test description")
                .color("update")
                .build();

        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .put("/api/calendar")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("calendar-update",
                        requestFields(
                                fieldWithPath("id").description("????????? ?????????"),
                                fieldWithPath("title").description("????????? ??????"),
                                fieldWithPath("description").description("????????? ??????"),
                                fieldWithPath("category").description("????????? ????????????"),
                                fieldWithPath("color").description("????????? ??????")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("?????? ??????"),
                                fieldWithPath("body.error").description("API ?????? ??????"),
                                fieldWithPath("body.result").description("API ??????????????????"),
                                fieldWithPath("body.data.calendarId").description("?????? ID"),
                                fieldWithPath("body.data.title").description("??????"),
                                fieldWithPath("body.data.description").description("??????"),
                                fieldWithPath("body.data.category").description("????????????"),
                                fieldWithPath("body.data.color").description("??????"),
                                fieldWithPath("statusCode").description("http status ????????????"),
                                fieldWithPath("statusCodeValue").description("http status ??????????????????")
                        )));
    }


}