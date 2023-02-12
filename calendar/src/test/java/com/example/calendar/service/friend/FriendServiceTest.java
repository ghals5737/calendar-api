package com.example.calendar.service.friend;

import com.example.calendar.domain.friend.FriendId;
import com.example.calendar.domain.noti.Noti;
import com.example.calendar.domain.noti.NotiType;
import com.example.calendar.domain.user.User;
import com.example.calendar.dto.friend.request.AcceptFriendRequest;
import com.example.calendar.dto.friend.request.RequestFriendRequest;
import com.example.calendar.dto.friend.response.AcceptFriendResponse;
import com.example.calendar.dto.friend.response.RequestFriendResponse;
import com.example.calendar.global.error.exception.CustomException;
import com.example.calendar.repository.friend.FriendRepository;
import com.example.calendar.repository.noti.NotiQueryDslRepository;
import com.example.calendar.repository.noti.NotiRepository;
import com.example.calendar.repository.user.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static com.example.calendar.global.error.ErrorCode.NOTI_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
@NoArgsConstructor
public class FriendServiceTest {

    @Autowired
    private FriendService friendService;

    @Autowired
    private NotiRepository notiRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotiQueryDslRepository notiQueryDslRepository;

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
                .email("receiveUser@gmail.com")
                .birthday(LocalDate.of(2023, 2, 26))
                .build());
    }

    @AfterEach
    void clear() {
        friendRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("친구 요청 API 정상 동작 확인 테스트")
    void requestFriendsTest() throws Exception {
        // given
        RequestFriendRequest request = RequestFriendRequest.builder()
                .sendUserId(sendUser.getId())
                .receiveUserId(receiveUser.getId())
                .build();

        // when
        RequestFriendResponse response = friendService.requestToBeFriends(request);
        Noti noti = notiRepository.findByReceiveUserId(response.getReceiveUserId())
                .orElseThrow(() -> new CustomException(NOTI_NOT_FOUND));

        // then
        assertThat(noti.getReceiveUserId()).isEqualTo(request.getReceiveUserId());
        assertThat(noti.getSendUserId()).isEqualTo(request.getSendUserId());
        assertThat(noti.getUseYn()).isEqualTo("Y");
        assertThat(noti.getNotiType()).isEqualTo(NotiType.FRIEND_REQUEST);

    }


    @Test
    @DisplayName("친구 요청 API 실패 테스트")
    void requestFriendsDuplicateTest() throws Exception {
        // given
        RequestFriendRequest request = RequestFriendRequest.builder()
                .sendUserId(sendUser.getId())
                .receiveUserId(receiveUser.getId())
                .build();

        // when
        friendService.requestToBeFriends(request);
        assertThrows(CustomException.class, () -> friendService.requestToBeFriends(request));

    }


    @Test
    @DisplayName("친구 수락 API 정상 동작 확인 테스트")
    void acceptToBeFriendsTest() throws Exception {
        // given

        // 친구 요청
        RequestFriendRequest request = RequestFriendRequest.builder()
                .sendUserId(sendUser.getId()) // 친구 요청 발신자
                .receiveUserId(receiveUser.getId())
                .build();

        // 친구 수락
        AcceptFriendRequest acceptRequest = AcceptFriendRequest.builder()
                .sendUserId(receiveUser.getId()) // 친구 수락 발신자 (처음 친구 신청의 수신자)
                .receiveUserId(sendUser.getId())
                .build();

        // when
        friendService.requestToBeFriends(request);
        AcceptFriendResponse response = friendService.acceptToBeFriends(acceptRequest);

        // then
        assertThat(acceptRequest.getSendUserId()).isEqualTo(response.getSendUserId());
        assertThat(acceptRequest.getReceiveUserId()).isEqualTo(response.getReceiveUserId());

        // 친구 테이블 확인

        assertThat(friendRepository.findById(FriendId.builder()
                .receiveUserId(acceptRequest.getReceiveUserId())
                .sendUserId(acceptRequest.getSendUserId())
                .build()).orElseThrow()).isNotNull();

        assertThat(friendRepository.findById(FriendId.builder()
                .receiveUserId(acceptRequest.getSendUserId())
                .sendUserId(acceptRequest.getReceiveUserId())
                .build()).orElseThrow()).isNotNull();

        // 알림 확인
        Noti noti = Optional.of(notiQueryDslRepository
                .findNotiBySendUserIdAndReceiveUserId(response.getSendUserId(), response.getReceiveUserId())).orElseThrow(
                () -> new CustomException(NOTI_NOT_FOUND)
        );

        assertThat(noti.getNotiType()).isEqualTo(NotiType.FRIEND_ACCEPT);
        assertThat(noti.getReceiveUserId()).isEqualTo(acceptRequest.getReceiveUserId());
        assertThat(noti.getSendUserId()).isEqualTo(acceptRequest.getSendUserId());

    }

//    @Test
//    @DisplayName("친구 거절 API 정상 동작 확인 테스트")
//    void refuseToBeFriendsTest() {
//        // given
//
//        // when
////        RefuseFriendResponse response = friendService.refuseToBeFriends(request);
//        RefuseFriendResponse response = notiService.refuseToBeFriendsById();
//        // then
//
//    }
}
