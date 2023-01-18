package com.example.calendar.controller.calendar;

import com.example.calendar.domain.calendar.Calendar;
import com.example.calendar.dto.calendar.request.CreateCalendarRequest;
import com.example.calendar.repository.calendar.CalendarRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
class CalendarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CalendarRepository calendarRepository;

//    @AfterEach
//    public void clear() {
//        calendarRepository.deleteAll();
//    }

    @Test
    @DisplayName("캘린더 생성 API 정상동작 확인")
    public void createCalendarTest() throws Exception {
        //given
        CreateCalendarRequest request = CreateCalendarRequest.builder()
                .title("test title")
                .color("test color")
                .category("test category")
                .description("test desc")
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
                                fieldWithPath("title").description("캘린더 제목"),
                                fieldWithPath("color").description("캘린더 색상"),
                                fieldWithPath("category").description("캘린더 카테고리"),
                                fieldWithPath("description").description("캘린더 설명")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("해더 정보"),
                                fieldWithPath("body.result").description("API 실행결과정보"),
                                fieldWithPath("body.data").description("바디"),
                                fieldWithPath("body.error").description("에러"),
                                fieldWithPath("statusCode").description("http status 상태코드"),
                                fieldWithPath("statusCodeValue").description("http status 상태숫자코드")
                        )));
    }

    @Test
    @DisplayName("캘린더 아이디로 단건 조회하는 API 정상동작 확인")
    public void selectCalendarByIdTest() throws Exception {
        //given
        Calendar expect = calendarRepository.save(Calendar.builder()
                .title("test title")
                .color("test color")
                .category("test category")
                .description("test desc")
                .build());

        log.info("expect.getId()={}", expect.getId());

        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .get("/api/calendar/{id}", expect.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("calendar-selectByID"
                        , pathParameters(
                                parameterWithName("id").description("조회할 일정 ID")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("해더 정보"),
                                fieldWithPath("body.result").description("API 실행결과정보"),
                                fieldWithPath("body.data.calendarId").description("달력 ID"),
                                fieldWithPath("body.data.scheduleId").description("일정 ID"),
                                fieldWithPath("body.data.title").description("제목"),
                                fieldWithPath("body.data.startDt").description("시작일"),
                                fieldWithPath("body.data.endDt").description("종료일"),
                                fieldWithPath("body.data.des").description("상세 설명"),
                                fieldWithPath("body.data.color").description("색깔"),
                                fieldWithPath("statusCode").description("http status 상태코드"),
                                fieldWithPath("statusCodeValue").description("http status 상태숫자코드")
                        )));
    }

    @Test
    @DisplayName("스케쥴아이디로 삭제하는 API 정상동작 확인")
    public void deleteScheduleByIdTest() throws Exception {
        //given
        Calendar expect = calendarRepository.save(Calendar.builder()
                .title("test title")
                .color("test color")
                .category("test category")
                .description("test desc")
                .build());

        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .delete("/api/calendar/{id}", expect.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("calendar-selectByID"
                        , pathParameters(
                                parameterWithName("id").description("조회할 일정 ID")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("해더 정보"),
                                fieldWithPath("statusCode").description("http status 상태코드"),
                                fieldWithPath("body.result").description("API 실행결과정보"),
                                fieldWithPath("body.data.scheduleId").description("삭제된 일정 ID"),
                                fieldWithPath("statusCodeValue").description("http status 상태숫자코드")
                        )));
    }
}