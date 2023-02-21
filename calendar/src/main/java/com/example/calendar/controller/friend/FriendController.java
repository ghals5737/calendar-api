package com.example.calendar.controller.friend;

import com.example.calendar.dto.friend.request.AcceptFriendRequest;
import com.example.calendar.dto.friend.request.RefuseFriendRequest;
import com.example.calendar.dto.friend.request.RequestFriendRequest;
import com.example.calendar.dto.friend.response.AcceptFriendResponse;
import com.example.calendar.dto.friend.response.RefuseFriendResponse;
import com.example.calendar.dto.friend.response.RequestFriendResponse;
import com.example.calendar.service.friend.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/friends")
public class FriendController {

    private final FriendService friendService;

    @PostMapping("request")
    public RequestFriendResponse requestToBeFriends(@RequestBody @Valid RequestFriendRequest request) throws Exception {
        return friendService.requestToBeFriends(request);
    }

    @PostMapping("accept")
    public AcceptFriendResponse acceptToBeFriends(@RequestBody @Valid AcceptFriendRequest request) {
        return friendService.acceptToBeFriends(request);
    }
    @PostMapping("refuse")
    public RefuseFriendResponse refuseToBeFriends(@RequestBody @Valid RefuseFriendRequest request) {
        return friendService.refuseToBeFriends(request);
    }
}
