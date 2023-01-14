package com.example.calendar.controller.schedule;

import com.example.calendar.domain.schedule.Schedule;
import com.example.calendar.dto.schedule.request.CreateScheduleRequest;
import com.example.calendar.repository.schedule.ScheduleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
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

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension.class)
public class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @AfterEach
    public void clear(){
        scheduleRepository.deleteAll();
    }

    @Test
    @DisplayName("스케쥴생성 API 정상동작 확인")
    public void createScheduleTest() throws Exception{
        //given
        CreateScheduleRequest request= CreateScheduleRequest.builder()
                .calendarId(1L)
                .title("test")
                .startDt(LocalDateTime.now())
                .endDt(LocalDateTime.now())
                .des("test des")
                .color("test color")
                .build();

        //when,then
        this.mockMvc.perform(
                        RestDocumentationRequestBuilders
                            .post("/api/schedule")
                            .content(objectMapper.writeValueAsString(request))
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk())
                            .andDo(document("schedule-api",
                                    requestFields(
                                            fieldWithPath("calendarId").description("달력 아이디"),
                                            fieldWithPath("title").description("일정 제목"),
                                            fieldWithPath("startDt").description("일정 시작일"),
                                            fieldWithPath("endDt").description("일정 종료일"),
                                            fieldWithPath("des").description("일정 상세"),
                                            fieldWithPath("color").description("일정 색")
                                    ),
                                    responseFields(
                                            fieldWithPath("headers").description("해더 정보"),
                                            fieldWithPath("body.result").description("API 실행결과정보"),
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
    @DisplayName("스케쥴아이디로 단건 조회하는 API 정상동작 확인")
    public void selectScheduleByIdTest() throws Exception{
        //given
        Schedule expect=scheduleRepository.save(Schedule.builder()
                .calendarId(1L)
                .title("test")
                .startDt(LocalDateTime.now())
                .endDt(LocalDateTime.now())
                .description("test")
                .color("test")
                .build());

        //when,then
        this.mockMvc.perform(
                RestDocumentationRequestBuilders
                        .get("/api/schedule/{scheduleId}", expect.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("schedule-api"
                        ,pathParameters(
                                parameterWithName("scheduleId").description("조회할 일정 ID")
                        )                        ,
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
}
