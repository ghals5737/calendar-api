package com.example.calendar.service.friend;

import com.example.calendar.domain.friend.Friend;
import com.example.calendar.domain.friend.FriendId;
import com.example.calendar.domain.noti.Noti;
import com.example.calendar.domain.noti.NotiType;
import com.example.calendar.dto.friend.request.AcceptFriendRequest;
import com.example.calendar.dto.friend.request.RefuseFriendRequest;
import com.example.calendar.dto.friend.request.RequestFriendRequest;
import com.example.calendar.dto.friend.response.*;
import com.example.calendar.global.error.ErrorCode;
import com.example.calendar.global.error.exception.CustomException;
import com.example.calendar.repository.friend.FriendQueryDslRepository;
import com.example.calendar.repository.friend.FriendRepository;
import com.example.calendar.repository.noti.NotiQueryDslRepository;
import com.example.calendar.repository.noti.NotiRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.example.calendar.global.error.ErrorCode.FRIEND_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Service
public class FriendService {
    private final FriendRepository friendRepository;
    private final NotiRepository notiRepository;
    private final NotiQueryDslRepository notiQueryDslRepository;

    private final FriendQueryDslRepository friendQueryDslRepository;

    @Transactional
    public RequestFriendResponse requestToBeFriends(RequestFriendRequest request) throws Exception {
        if (notiQueryDslRepository.existsNoti(request))
            throw new CustomException(ErrorCode.DUPLICATE_FRIEND_REQUEST);
        return FriendResponse.toRequestFriendResponse(notiRepository.save(Noti.builder()
                .sendUserId(request.getSendUserId())
                .useYn("Y")
                .notiType(NotiType.FRIEND_REQUEST)
                .receiveUserId(request.getReceiveUserId())
                .regDtm(LocalDateTime.now())
                .build()));
    }

    @Transactional
    public AcceptFriendResponse acceptToBeFriends(AcceptFriendRequest request) {
        notiQueryDslRepository.updateNotiTypeAccept(request);

        notiRepository.save(Noti.builder()
                .sendUserId(request.getSendUserId())
                .receiveUserId(request.getReceiveUserId())
                .notiType(NotiType.FRIEND_ACCEPT)
                .useYn("Y")
                .regDtm(LocalDateTime.now())
                .build());

        FriendId friendIdReverse = FriendId.builder()
                .sendUserId(request.getReceiveUserId())
                .receiveUserId(request.getSendUserId())
                .build();
        saveFriend(friendIdReverse);

        FriendId friendId = FriendId.builder()
                .sendUserId(request.getSendUserId())
                .receiveUserId(request.getReceiveUserId())
                .build();

        return FriendResponse.toAcceptFriendResponse(saveFriend(friendId));
    }

    private Friend saveFriend(FriendId friendId) {
        return friendRepository.save(Friend.builder()
                .id(friendId)
                .build());
    }

    @Transactional
    public RefuseFriendResponse refuseToBeFriends(RefuseFriendRequest request) {
        notiQueryDslRepository.updateUseYnNById(request.getNotiId());
        return FriendResponse.toRefuseFriendResponse(notiRepository.save(Noti.builder()
                .sendUserId(request.getSendUserId())
                .receiveUserId(request.getReceiveUserId())
                .notiType(NotiType.FRIEND_DECLINE)
                .regDtm(LocalDateTime.now())
                .useYn("Y")
                .build()));
    }

    public List<SelectFriendListResponse> selectFriendList(Long userId) {
        return friendQueryDslRepository.selectFriendList(userId);
    }
}
