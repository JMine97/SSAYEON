package a204.ssayeon.api.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserShowMessageListRes {
    private Long id;
    private Long senderId;
    private String senderNickname;
    private String description;
    private LocalDateTime createdAt;
    private Integer unReadCnt;
    private Long otherUserId;
}
