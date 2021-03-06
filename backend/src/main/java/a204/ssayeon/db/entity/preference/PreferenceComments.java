package a204.ssayeon.db.entity.preference;

import a204.ssayeon.db.entity.BaseEntity;
import a204.ssayeon.db.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter @Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PreferenceComments extends BaseEntity {

    @Id @GeneratedValue
    @Column(name="preference_comments_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "preference_id")
    private Preference preference;

    private String description;

    public void setDescription(String description) {
        this.description = description;
    }
}
