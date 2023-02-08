package com.example.calendar.service.friend;

import com.example.calendar.domain.friend.Friend;
import com.example.calendar.domain.friend.FriendId;
import com.example.calendar.domain.user.User;
import com.example.calendar.dto.friend.request.AcceptFriendRequest;
import com.example.calendar.dto.friend.response.AcceptFriendResponse;
import com.example.calendar.global.error.exception.CustomException;
import com.example.calendar.repository.friend.FriendRepository;
import com.example.calendar.repository.user.UserRepository;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static com.example.calendar.global.error.ErrorCode.FRIEND_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@NoArgsConstructor
public class FriendServiceTest {

    @Autowired
    private FriendService friendService;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    private User mainUser;
    private User subUser;

    @BeforeEach
    void createNoti() {
        mainUser = userRepository.save(User.builder()
                .nickname("mainUser")
                .password("pw")
                .email("mainUser@gmail.com")
                .birthday(LocalDate.of(2023, 1, 26))
                .build());
        subUser = userRepository.save(User.builder()
                .nickname("subUser")
                .password("pw")
                .email("subUser@gmail.com")
                .birthday(LocalDate.of(2023, 2, 26))
                .build());
        userRepository.save(mainUser);
        userRepository.save(subUser);
    }

    @AfterEach
    void clear() {
        friendRepository.deleteAll();
    }

    @Test
    void acceptToBeFriendsTest() {
        // given
        AcceptFriendRequest request = AcceptFriendRequest.builder()
                .mainUserId(mainUser.getId())
                .subUserId(subUser.getId())
                .build();

        // when
        AcceptFriendResponse response = friendService.acceptToBeFriends(request);

        // then

        // Main -> Sub
        assertThat(request.getMainUserId()).isEqualTo(response.getMainUserId());
        assertThat(request.getSubUserId()).isEqualTo(response.getSubUserId());

        // Sub -> Main
        FriendId friendIdReverse = FriendId.builder()
                .mainUserId(response.getSubUserId())
                .subUserId(response.getMainUserId())
                .build();

        Friend friend = friendRepository.findById(friendIdReverse)
                .orElseThrow(() -> new CustomException(FRIEND_NOT_FOUND));

        assertThat(friend.getId().getSubUserId()).isEqualTo(response.getMainUserId());

    }

}
