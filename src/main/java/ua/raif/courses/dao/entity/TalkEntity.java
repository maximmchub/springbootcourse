package ua.raif.courses.dao.entity;

import lombok.*;

import javax.persistence.*;

@ToString
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@Table(name = "talks")
public class TalkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String caption;

    @NonNull
    private String description;

    @NonNull
    String speaker;

    @NonNull
    String talkType;

    @ManyToOne
    @JoinColumn(name = "conference_id", insertable = false, updatable = false)
    ConferenceEntity conference;

}
