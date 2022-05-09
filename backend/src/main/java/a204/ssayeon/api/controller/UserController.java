package a204.ssayeon.api.controller;

import a204.ssayeon.api.request.user.*;
import a204.ssayeon.api.response.user.UserShowAlarmRes;
import a204.ssayeon.api.response.user.UserShowMessageDetail;
import a204.ssayeon.api.response.user.UserShowMessageList;
import a204.ssayeon.api.service.UserService;
import a204.ssayeon.common.model.enums.Status;
import a204.ssayeon.common.model.response.AdvancedResponseBody;
import a204.ssayeon.common.model.response.PaginationResponseBody;
import a204.ssayeon.config.auth.CurrentUser;
import a204.ssayeon.config.auth.PrincipalDetails;
import a204.ssayeon.db.entity.Pagination;
import a204.ssayeon.db.entity.user.Alarm;
import a204.ssayeon.db.entity.user.Message;
import a204.ssayeon.db.entity.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/message/list")
    public AdvancedResponseBody<List<UserShowMessageList>> showMessageList(@CurrentUser User user, @PageableDefault(sort = "message_id", direction = Sort.Direction.DESC, size=10) Pageable pageable){
        Page<Message> MessagePage = userService.showMessageList(user, pageable);
        Pagination pagination = Pagination.getPagination(MessagePage);
        List<UserShowMessageList> messageList = new ArrayList<>();

        MessagePage.forEach((message) -> {
            messageList.add(UserShowMessageList.builder().created_at(message.getCreatedAt()).description(message.getDescription()).id(message.getId())
                    .receiver_id(message.getReceiver().getId()).receiver_nickname(message.getReceiver().getNickname()).sender_id(message.getSender().getId()).sender_nickname(message.getSender().getNickname()).build());
        });
        return PaginationResponseBody.of(Status.OK, messageList, pagination);
    }

    @GetMapping("/message/{otherUserId}")
    public AdvancedResponseBody<List<UserShowMessageDetail>> showMessageDetail(@CurrentUser User user, @PathVariable Long otherUserId, @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=10) Pageable pageable){
        Page<Message> MessagePage = userService.showMessageDetail(user, otherUserId, pageable);
        Pagination pagination = Pagination.getPagination(MessagePage);
        List<UserShowMessageDetail> messageList = new ArrayList<>();

        MessagePage.forEach((message) -> {
            messageList.add(UserShowMessageDetail.builder().created_at(message.getCreatedAt()).description(message.getDescription()).id(message.getId())
                    .receiver_id(message.getReceiver().getId()).receiver_nickname(message.getReceiver().getNickname()).sender_id(message.getSender().getId()).sender_nickname(message.getSender().getNickname()).build());
        });
        return PaginationResponseBody.of(Status.OK, messageList, pagination);
    }

    @PostMapping("/message/{toUserId}")
    public AdvancedResponseBody<String> sendMessage(@CurrentUser User user, @PathVariable Long toUserId, @RequestBody UserSendMessageReq userSendMessageReq){
        userService.sendMessage(user, toUserId, userSendMessageReq.getDescription());
        return AdvancedResponseBody.of(Status.OK);
    }


    @PatchMapping("/{id}")
    public AdvancedResponseBody<String> editUser(@CurrentUser User user, @ModelAttribute UserEditUserReq userEditUserReq) throws IOException {
        userService.editUser(user, userEditUserReq);
        return AdvancedResponseBody.of(Status.OK);
    }

    @PatchMapping("/{id}/password")
    public AdvancedResponseBody<String> editPassword(@CurrentUser User user, @RequestBody UserEditPasswordReq userEditPasswordReq) {
        userService.editPassword(user, userEditPasswordReq);
        return AdvancedResponseBody.of(Status.OK);
    }

    @GetMapping("/search")
    public AdvancedResponseBody<List<UserFindUserRes>> findUser(@RequestParam String word, @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=10) Pageable pageable) {
        Page<User> userPage = userService.findUser(word, pageable);
        Pagination pagination = Pagination.getPagination(userPage);
        List<UserFindUserRes> userList = new ArrayList<>();

        userPage.forEach((user) -> {
            userList.add(UserFindUserRes.builder().nickname(user.getNickname()).id(user.getId()).picture(user.getPicture()).build());
        });
        return PaginationResponseBody.of(Status.OK, userList, pagination);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public AdvancedResponseBody<String> deleteUser(@CurrentUser User user) {
        userService.deleteUser(user);
        return AdvancedResponseBody.of(Status.NO_CONTENT);
    }

    @GetMapping("/alarm") //todo : currentUser 없으면 에러 던지기
    public AdvancedResponseBody<List<UserShowAlarmRes>> showAlarm(@CurrentUser User user, @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size=10) Pageable pageable) {
        Page<Alarm> alarmPage = userService.showAlarm(user, pageable);
        Pagination pagination = Pagination.getPagination(alarmPage);
        List<UserShowAlarmRes> alarmList = new ArrayList<>();

        alarmPage.forEach((alarm) -> {
            alarmList.add(UserShowAlarmRes.builder().description(alarm.getDescription()).id(alarm.getId()).isRead(alarm.getIsRead()).url(alarm.getUrl()).build());
        });
        return PaginationResponseBody.of(Status.OK, alarmList, pagination);
    }

    @PostMapping("/send-alarm")
    public AdvancedResponseBody<String> sendAlarm(@CurrentUser User user) {
        userService.sendAlarm(user);
        return AdvancedResponseBody.of(Status.OK);
    }

    @PostMapping("/read-alarm")
    public AdvancedResponseBody<String> readAlarm(@RequestBody UserAlarmReadReq alarmReadReq) {
        userService.readAlarm(alarmReadReq.getAlarmId());
        return AdvancedResponseBody.of(Status.OK);
    }
}
