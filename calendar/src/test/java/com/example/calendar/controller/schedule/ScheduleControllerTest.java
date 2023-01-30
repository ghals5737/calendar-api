package com.example.calendar.controller.schedule;

import com.example.calendar.domain.schedule.Schedule;
import com.example.calendar.dto.schedule.request.CreateScheduleRequest;
import com.example.calendar.dto.schedule.request.UpdateScheduleRequest;
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
import static org.springframework.restdocs.request.RequestDocumentation.*;
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
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk())
                            .andDo(document("schedule-create",
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
                .andDo(document("schedule-selectByID"
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

    @Test
    @DisplayName("스케쥴아이디로 삭제하는 API 정상동작 확인")
    public void deleteScheduleByIdTest() throws Exception{
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
                                .delete("/api/schedule/{scheduleId}", expect.getId())
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("schedule-delete"
                        ,pathParameters(
                                parameterWithName("scheduleId").description("조회할 일정 ID")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("해더 정보"),
                                fieldWithPath("statusCode").description("http status 상태코드"),
                                fieldWithPath("body.result").description("API 실행결과정보"),
                                fieldWithPath("body.data.scheduleId").description("삭제된 일정 ID"),
                                fieldWithPath("statusCodeValue").description("http status 상태숫자코드")
                        )));
    }

    @Test
    @DisplayName("스케쥴수정 API 정상동작 확인")
    public void updateScheduleTest() throws Exception{
        //given
        Schedule expect=scheduleRepository.save(Schedule.builder()
                .calendarId(1L)
                .title("test")
                .startDt(LocalDateTime.now())
                .endDt(LocalDateTime.now())
                .description("test")
                .color("test")
                .build());
        UpdateScheduleRequest request= UpdateScheduleRequest.builder()
                .scheduleId(expect.getId())
                .title("update test")
                .startDt(LocalDateTime.now())
                .endDt(LocalDateTime.now())
                .des("update test des")
                .color("update test color")
                .build();

        //when,then
        this.mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .put("/api/schedule")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("schedule-update",
                        requestFields(
                                fieldWithPath("scheduleId").description("일정 아이디"),
                                fieldWithPath("title").description("일정 제목"),
                                fieldWithPath("startDt").description("일정 시작일"),
                                fieldWithPath("endDt").description("일정 종료일"),
                                fieldWithPath("des").description("일정 상세"),
                                fieldWithPath("color").description("일정 색")
                        ),
                        responseFields(
                                fieldWithPath("headers").description("해더 정보"),
                                fieldWithPath("body.error").description("API 에러 정보??"),
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
    @DisplayName("달력ID,시작년월일,끝년월일로 일정리스트 조회하는 API 정상동작 확인")
    public void selectScheduleListTest() throws Exception{
        //given
        //2023년 01 월 기준
        Long calendarId=1L;
        String startYmd="20230101";
        String endYmd="20230204";

        //시작날짜 끝날짜 모두 조회하고 싶은 월안에 있음
        Schedule expect1=scheduleRepository.save(Schedule.builder()
                .calendarId(1L)
                .title("test1")
                .startDt(LocalDateTime.now())
                .endDt(LocalDateTime.now())
                .startYmd("20230102")
                .endYmd("20230112")
                .description("test1")
                .color("test1")
                .build());

        //시작날짜는 전월 끝날짜는 포함
        Schedule expect2=scheduleRepository.save(Schedule.builder()
                .calendarId(1L)
                .title("test2")
                .startDt(LocalDateTime.now())
                .endDt(LocalDateTime.now())
                .startYmd("20221202")
                .endYmd("20230106")
                .description("test2")
                .color("test2")
                .build());

        //시작날짜는 포함 끝날짜는 다음월
        Schedule expect3=scheduleRepository.save(Schedule.builder()
                .calendarId(1L)
                .title("test3")
                .startDt(LocalDateTime.now())
                .endDt(LocalDateTime.now())
                .startYmd("20230102")
                .endYmd("20230206")
                .description("test3")
                .color("test3")
                .build());

        //시작,끝날짜 모두 포함 X
        Schedule expect4=scheduleRepository.save(Schedule.builder()
                .calendarId(1L)
                .title("test4")
                .startDt(LocalDateTime.now())
                .endDt(LocalDateTime.now())
                .startYmd("20221202")
                .endYmd("20230206")
                .description("test4")
                .color("test4")
                .build());

        //when,then
        this.mockMvc.perform(
                        RestDocumentationRequestBuilders
                                .get("/api/schedule/calendarId/{calendarId}?startYmd="+startYmd+"&endYmd="+endYmd, calendarId)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("schedule-selectByID"
                        ,pathParameters(
                                parameterWithName("calendarId").description("조회할 달력 ID")
                        ),
                        requestParameters(
                                parameterWithName("startYmd").description("조회할 시작년월일"),
                                parameterWithName("endYmd").description("조회할 끝년월일")
                        )
                        ,responseFields(
                                fieldWithPath("headers").description("해더 정보"),
                                fieldWithPath("body.result").description("API 실행결과정보"),
                                fieldWithPath("body.data.[].calendarId").description("달력 ID"),
                                fieldWithPath("body.data.[].scheduleId").description("일정 ID"),
                                fieldWithPath("body.data.[].title").description("제목"),
                                fieldWithPath("body.data.[].startYmd").description("시작년월일"),
                                fieldWithPath("body.data.[].endYmd").description("종료년월일"),
                                fieldWithPath("body.data.[].startDt").description("시작일"),
                                fieldWithPath("body.data.[].endDt").description("종료일"),
                                fieldWithPath("body.data.[].des").description("상세 설명"),
                                fieldWithPath("body.data.[].color").description("색깔"),
                                fieldWithPath("body.error").description("http status 상태코드"),
                                fieldWithPath("statusCode").description("에러코드"),
                                fieldWithPath("statusCodeValue").description("http status 상태숫자코드")
                        )));
    }
}
