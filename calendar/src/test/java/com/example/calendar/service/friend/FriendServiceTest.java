package com.example.calendar.service.friend;

import com.example.calendar.domain.noti.Noti;
import com.example.calendar.domain.noti.NotiType;
import com.example.calendar.domain.user.User;
import com.example.calendar.dto.friend.request.RequestFriendRequest;
import com.example.calendar.dto.friend.response.RequestFriendResponse;
import com.example.calendar.global.error.exception.CustomException;
import com.example.calendar.repository.friend.FriendRepository;
import com.example.calendar.repository.noti.NotiRepository;
import com.example.calendar.repository.user.UserRepository;
import com.example.calendar.service.noti.NotiService;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static com.example.calendar.global.error.ErrorCode.NOTI_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@NoArgsConstructor
public class FriendServiceTest {

    @Autowired
    private FriendService friendService;

    @Autowired
    private NotiService notiService;
    @Autowired
    private NotiRepository notiRepository;
    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

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
    void requestFriendsTest() throws Exception{
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
        assertThat(noti.getId()).isEqualTo(response.getNotiId());
        assertThat(noti.getReceiveUserId()).isEqualTo(response.getReceiveUserId());
        assertThat(noti.getSendUserId()).isEqualTo(response.getSendUserId());
        assertThat(noti.getUseYn()).isEqualTo("Y");
        assertThat(noti.getNotiType()).isEqualTo(NotiType.FRIEND_REQUEST);
    }


    @Test
    @DisplayName("친구 요청 API 실패 테스트")
    void requestFriendsDuplicateTest() throws Exception{
        // given
        RequestFriendRequest request = RequestFriendRequest.builder()
                .sendUserId(sendUser.getId())
                .receiveUserId(receiveUser.getId())
                .build();

        // when
        friendService.requestToBeFriends(request);
        assertThrows(CustomException.class, () -> friendService.requestToBeFriends(request));

    }

//
//    @Test
//    @DisplayName("친구 수락 API 정상 동작 확인 테스트")
//    void acceptToBeFriendsTest() {
//        // given
//        AcceptFriendRequest request = AcceptFriendRequest.builder()
//                .mainUserId(mainUser.getId())
//                .subUserId(subUser.getId())
//                .build();
//
//        // when
//        AcceptFriendResponse response = friendService.acceptToBeFriends(request);
//
//        // then
//
//        // Main -> Sub
//        assertThat(request.getMainUserId()).isEqualTo(response.getMainUserId());
//        assertThat(request.getSubUserId()).isEqualTo(response.getSubUserId());
//
//        // Sub -> Main
//        FriendId friendIdReverse = FriendId.builder()
//                .mainUserId(response.getSubUserId())
//                .subUserId(response.getMainUserId())
//                .build();
//
//        Friend friend = friendRepository.findById(friendIdReverse)
//                .orElseThrow(() -> new CustomException(FRIEND_NOT_FOUND));
//
//        assertThat(friend.getId().getSubUserId()).isEqualTo(response.getMainUserId());
//
//
//        // todo 알림 테이블에 쌓아야한다. 각각의 유저한테.
//    }

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
