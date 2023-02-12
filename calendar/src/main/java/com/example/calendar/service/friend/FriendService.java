package com.example.calendar.service.friend;

import com.example.calendar.domain.friend.Friend;
import com.example.calendar.domain.friend.FriendId;
import com.example.calendar.domain.noti.Noti;
import com.example.calendar.domain.noti.NotiType;
import com.example.calendar.dto.friend.request.AcceptFriendRequest;
import com.example.calendar.dto.friend.request.RefuseFriendRequest;
import com.example.calendar.dto.friend.request.RequestFriendRequest;
import com.example.calendar.dto.friend.response.AcceptFriendResponse;
import com.example.calendar.dto.friend.response.FriendResponse;
import com.example.calendar.dto.friend.response.RefuseFriendResponse;
import com.example.calendar.dto.friend.response.RequestFriendResponse;
import com.example.calendar.global.error.ErrorCode;
import com.example.calendar.global.error.exception.CustomException;
import com.example.calendar.repository.friend.FriendRepository;
import com.example.calendar.repository.noti.NotiQueryDslRepository;
import com.example.calendar.repository.noti.NotiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final NotiRepository notiRepository;
    private final NotiQueryDslRepository notiQueryDslRepository;

    @Transactional
    public RequestFriendResponse requestToBeFriends(RequestFriendRequest request) throws Exception {
        if (notiQueryDslRepository.existsNoti(request))
            throw new CustomException(ErrorCode.DUPLICATE_FRIEND_REQUEST);
        return FriendResponse.toRequestFriendResponse(
                notiRepository.save(Noti.builder()
                        .sendUserId(request.getSendUserId())
                        .useYn("Y")
                        .notiType(NotiType.FRIEND_REQUEST)
                        .receiveUserId(request.getReceiveUserId())
                        .regDtm(LocalDateTime.now())
                        .build()));
    }

    @Transactional
    public AcceptFriendResponse acceptToBeFriends(AcceptFriendRequest request) {
        FriendId friendId = getFriendId(request.getSendUserId(), request.getReceiveUserId());
        FriendId friendIdReverse = getFriendId(request.getReceiveUserId(), request.getSendUserId());
        saveFriend(friendIdReverse);
//        notiService.saveAcceptNotiForBoth()
        return FriendResponse.toAcceptFriendResponse(saveFriend(friendId));
    }

    private Friend saveFriend(FriendId friendId) {
        return friendRepository.save(Friend.builder()
                .id(friendId)
                .build());
    }

    private FriendId getFriendId(Long sendId, Long receiveId) {
        return FriendId.builder()
                .sendUserId(sendId)
                .receiveUserId(receiveId)
                .build();
    }


}
