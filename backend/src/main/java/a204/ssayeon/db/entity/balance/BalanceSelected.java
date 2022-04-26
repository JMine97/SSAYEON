package a204.ssayeon.db.entity.balance;

import a204.ssayeon.db.entity.BaseEntity;
import a204.ssayeon.db.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BalanceSelected extends BaseEntity {

    @Id @GeneratedValue
    @Column(name="balance_selected_id")
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "balance_id")
    private Balance balance;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Boolean selected;
}
