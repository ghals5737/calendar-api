package com.example.calendar.service.friend;

import com.example.calendar.domain.friend.Friend;
import com.example.calendar.domain.friend.FriendId;
import com.example.calendar.dto.friend.request.AcceptFriendRequest;
import com.example.calendar.dto.friend.response.FriendResponse;
import com.example.calendar.dto.friend.response.AcceptFriendResponse;
import com.example.calendar.repository.friend.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FriendService {
    private final FriendRepository friendRepository;

    public AcceptFriendResponse acceptToBeFriends(AcceptFriendRequest request) {
        FriendId friendId = getFriendId(request.getMainUserId(), request.getSubUserId());
        FriendId friendIdReverse = getFriendId(request.getSubUserId(), request.getMainUserId());
        saveFriend(friendIdReverse);
        return FriendResponse.toRequestFriendResponse(saveFriend(friendId));
    }

    private Friend saveFriend(FriendId friendId) {
        return friendRepository.save(Friend.builder()
                .id(friendId)
                .build());
    }

    private FriendId getFriendId(Long mainUserId, Long subUserId) {
        return FriendId.builder()
                .mainUserId(mainUserId)
                .subUserId(subUserId)
                .build();
    }
}
