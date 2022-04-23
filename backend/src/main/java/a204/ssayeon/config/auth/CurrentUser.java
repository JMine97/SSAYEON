package a204.ssayeon.config.auth;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

// 런타임 까지 유지
@Retention(RetentionPolicy.RUNTIME)
// 타겟은 파라미터에만 붙이겠다.
@Target(ElementType.PARAMETER)
// 익명 사용자인 경우에는 null로, 익명 사용자가 아닌 경우에는 실제 account 객체로
// Principal 을 다이나믹 하게 꺼내기 위해 @CurrentUser 생성
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account")
public @interface CurrentUser {
}